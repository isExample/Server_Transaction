package HeyPorori.transaction.service;

import HeyPorori.transaction.domain.Category;
import HeyPorori.transaction.domain.Transaction;
import HeyPorori.transaction.dto.PostReq;
import HeyPorori.transaction.dto.PostRes;
import HeyPorori.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final TransactionAttachService transactionAttachService;

    public void createPost(String token, PostReq postReq) {
        userService.sendTestJwtRequest(token);
        Transaction txn = Transaction.toEntity(postReq, userService.getUserId(token));
        txn.setAttachList(transactionAttachService.savePostAttach(txn, postReq.getImageNameList()));
        transactionRepository.save(txn);
    }

    public List<PostRes> findAllPostByCategory(String category){
        List<Transaction> txnList = new ArrayList<>();
        if(category.equals("NONE")){
            txnList = transactionRepository.findByStatus("ACTIVE");
        } else{
            txnList = transactionRepository.findByCategoryAndStatus(Category.parsing(category), "ACTIVE");
        }

        List<PostRes> postResList = new ArrayList<>();
        for(Transaction txn: txnList){
            PostRes postRes = PostRes.toDto(txn, transactionAttachService.getFirstImageName(txn));
            postResList.add(postRes);
        }

        return postResList;
    }
}
