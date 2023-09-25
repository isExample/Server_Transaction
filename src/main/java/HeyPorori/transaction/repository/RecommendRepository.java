package HeyPorori.transaction.repository;

import HeyPorori.transaction.domain.Recommend;
import HeyPorori.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    boolean existsByTransactionIdAndUserId(Transaction transaction, Long userId);
}
