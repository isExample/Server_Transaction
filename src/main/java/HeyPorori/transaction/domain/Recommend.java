package HeyPorori.transaction.domain;

import HeyPorori.transaction.config.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recommend")
public class Recommend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long recommendId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id")
    private Transaction transactionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ColumnDefault("'ACTIVE'")
    private String status;

    @Builder
    public Recommend(Transaction transactionId, Long userId){
        this.transactionId = transactionId;
        this.userId = userId;
    }
}