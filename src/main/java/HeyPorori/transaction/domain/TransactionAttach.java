package HeyPorori.transaction.domain;

import HeyPorori.transaction.config.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "transaction_attach")
@DynamicUpdate
public class TransactionAttach extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_id")
    private Long attachId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id")
    private Transaction transactionId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Builder
    public TransactionAttach(Transaction transactionId, String imageUrl){
        this.transactionId = transactionId;
        this.imageUrl = imageUrl;
    }
}
