package HeyPorori.transaction.service;

import HeyPorori.transaction.domain.Transaction;
import HeyPorori.transaction.dto.PostReq;
import HeyPorori.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public void createPost(String token, PostReq postReq) {
        userService.sendTestJwtRequest(token);
        transactionRepository.save(Transaction.toEntity(postReq, userService.getUserId(token)));
    }
}
