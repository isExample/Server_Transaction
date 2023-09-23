package HeyPorori.transaction.service;

import HeyPorori.transaction.config.BaseException;
import HeyPorori.transaction.config.BaseResponseStatus;
import HeyPorori.transaction.domain.Category;
import HeyPorori.transaction.domain.Transaction;
import HeyPorori.transaction.dto.CreatePostReq;
import HeyPorori.transaction.dto.PostDetailRes;
import HeyPorori.transaction.dto.PostsRes;
import HeyPorori.transaction.dto.UserInfoRes;
import HeyPorori.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final TransactionAttachService transactionAttachService;

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
        PostDetailRes postDetailRes = PostDetailRes.toDto(txn, userInfoRes.getNickName(), toFormattedDate(txn.getCreatedAt()), imageNameList, isOwner);
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

    public String toFormattedDate(LocalDateTime baseDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return baseDateTime.format(formatter);
    }
}
