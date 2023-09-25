package HeyPorori.transaction.service;

import HeyPorori.transaction.config.BaseException;
import HeyPorori.transaction.config.BaseResponseStatus;
import HeyPorori.transaction.domain.Category;
import HeyPorori.transaction.domain.Recommend;
import HeyPorori.transaction.domain.Transaction;
import HeyPorori.transaction.dto.*;
import HeyPorori.transaction.repository.RecommendRepository;
import HeyPorori.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final TransactionAttachService transactionAttachService;
    private final RecommendRepository recommendRepository;

    public void createPost(String token, CreatePostReq postReq) {
        userService.sendTestJwtRequest(token);
        Transaction txn = Transaction.toEntity(postReq, userService.getUserId(token));
        txn.setAttachList(transactionAttachService.savePostAttach(txn, postReq.getImageNameList()));
        transactionRepository.save(txn);
    }

    public List<PostsRes> findAllPostByCategory(String category){
        List<Transaction> txnList = new ArrayList<>();
        if(category.equals("NONE")){
            txnList = transactionRepository.findByStatus("ACTIVE");
        } else{
            txnList = transactionRepository.findByCategoryAndStatus(Category.parsing(category), "ACTIVE");
        }

        List<PostsRes> postResList = new ArrayList<>();
        for(Transaction txn: txnList){
            PostsRes postRes = PostsRes.toDto(txn, transactionAttachService.getFirstImageName(txn));
            postResList.add(postRes);
        }

        return postResList;
    }

    public PostDetailRes getPostDetail(String token, Long transactionId){
        Transaction txn = transactionRepository.findByTransactionIdAndStatus(transactionId, "ACTIVE")
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        UserInfoRes userInfoRes = userService.getUserIdAndNickName(token);
        boolean isOwner = false;
        if(userInfoRes.getUserId() == txn.getUserId()) isOwner = true;
        List<String> imageNameList = transactionAttachService.getImageNameList(txn);
        boolean isRecommends = recommendRepository.existsByTransactionIdAndUserId(txn, userInfoRes.getUserId());
        PostDetailRes postDetailRes = PostDetailRes.toDto(txn, userInfoRes.getNickName(), toFormattedDate(txn.getCreatedAt()), imageNameList, isOwner, isRecommends);
        return postDetailRes;
    }

    public void deletePost(String token, Long transactionId){
        Long userId = userService.getUserId(token);
        Transaction txn = transactionRepository.findByTransactionIdAndStatus(transactionId, "ACTIVE")
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        if(userId != txn.getUserId()) throw new BaseException(BaseResponseStatus.INVALID_POST_OWNER);
        txn.changeStatus("INACTIVE");
        transactionRepository.save(txn);
    }

    public void recommendPost(String token, RecommendPostReq req){
        Long userId = userService.getUserId(token);
        Transaction txn = transactionRepository.findByTransactionIdAndStatus(req.getTransactionId(), "ACTIVE")
                .orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));
        Recommend recommend = recommendRepository.findByTransactionIdAndUserIdAndStatus(txn, userId, req.getFromStatus());
        if(recommend == null && req.getToStatus().equals("ACTIVE")){
            Recommend newRecommend = Recommend.toEntity(txn, userId);
            recommendRepository.save(newRecommend);
        } else{
            if(recommend != null && recommend.getStatus().equals(req.getFromStatus())){
                recommend.changeStatus(req.getToStatus());
            } else{
                new IllegalArgumentException("추천 상태가 예상된 상태와 같지 않습니다.");
            }
        }
    }

    public String toFormattedDate(LocalDateTime baseDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return baseDateTime.format(formatter);
    }
}
