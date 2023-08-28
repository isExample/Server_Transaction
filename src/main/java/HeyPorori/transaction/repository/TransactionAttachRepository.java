package HeyPorori.transaction.repository;

import HeyPorori.transaction.domain.TransactionAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionAttachRepository extends JpaRepository<TransactionAttach, Long> {
}
