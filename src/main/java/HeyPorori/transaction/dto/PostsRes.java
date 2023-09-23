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
    private String title;
    private String imageName;
    private String address;
    private int recommend;

    public static PostsRes toDto(Transaction transaction, String imageName){
        return PostsRes.builder()
                .title(transaction.getTitle())
                .imageName(imageName)
                .address(transaction.getAddress())
                .recommend(transaction.getRecommendList().size())
                .build();
    }
}
