package HeyPorori.transaction.service;

import HeyPorori.transaction.domain.Transaction;
import HeyPorori.transaction.domain.TransactionAttach;
import HeyPorori.transaction.repository.TransactionAttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionAttachService {
    private final TransactionAttachRepository transactionAttachRepository;

    public List<TransactionAttach> savePostAttach(Transaction transaction, List<String> imageNameList){
        List<TransactionAttach> TransactionAttachList = new ArrayList<>();
        for(String imageName: imageNameList){
            TransactionAttachList.add(TransactionAttach.toEntity(transaction, imageName));
        }
        return TransactionAttachList;
    }
}
