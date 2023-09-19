package HeyPorori.transaction.domain;

import HeyPorori.transaction.config.BaseTimeEntity;
import HeyPorori.transaction.dto.PostReq;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "transaction")
@DynamicInsert
@DynamicUpdate
public class Transaction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String title;

    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String content;

    @ColumnDefault("0")
    private int recommend;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = true)
    private Category category;

    @ColumnDefault("'ACTIVE'")
    private String status;

    @OneToMany(mappedBy = "transactionId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<TransactionAttach> attachList = new ArrayList<>();

    @OneToMany(mappedBy = "transactionId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Recommend> recommendList = new ArrayList<>();

    @Builder
    public Transaction(Long userId, String title, String content, int recommend, String address, Double latitude, Double longitude, Category category, String status){
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.recommend = recommend;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.status = status;
    }

    public static Transaction toEntity(PostReq postReq, Long userId){
        return Transaction.builder()
                .userId(userId)
                .title(postReq.getTitle())
                .content(postReq.getContent())
                .recommend(0)
                .address("필동")
                .latitude(0.0)
                .longitude(0.0)
                .category(Category.parsing(postReq.getCategory()))
                .build();
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}