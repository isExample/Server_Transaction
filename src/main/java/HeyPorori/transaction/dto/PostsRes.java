package HeyPorori.transaction.dto;

import HeyPorori.transaction.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsRes {
    private Long transactionId;
    private String title;
    private String imageName;
    private String address;
    private int recommend;

    public static PostsRes toDto(Transaction transaction, String imageName){
        return PostsRes.builder()
                .transactionId(transaction.getTransactionId())
                .title(transaction.getTitle())
                .imageName(imageName)
                .address(transaction.getAddress())
                .recommend(transaction.getRecommendList().size())
                .build();
    }
}
