package HeyPorori.transaction.domain;

import HeyPorori.transaction.config.BaseTimeEntity;
import HeyPorori.transaction.dto.CreatePostReq;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recommend")
@DynamicInsert
@DynamicUpdate
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
    public Recommend(Transaction transactionId, Long userId, String status){
        this.transactionId = transactionId;
        this.userId = userId;
        this.status = status;
    }

    public static Recommend toEntity(Transaction transaction, Long userId){
        return Recommend.builder()
                .transactionId(transaction)
                .userId(userId)
                .build();
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}