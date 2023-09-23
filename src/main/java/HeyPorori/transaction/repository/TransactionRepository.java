package HeyPorori.transaction.repository;

import HeyPorori.transaction.domain.Category;
import HeyPorori.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStatus(String status);
    Transaction findByTransactionIdAndStatus(Long transactionId, String status);
    List<Transaction> findByCategoryAndStatus(Category category, String status);
}
