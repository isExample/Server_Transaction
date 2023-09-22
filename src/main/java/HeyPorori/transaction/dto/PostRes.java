package HeyPorori.transaction.dto;

import HeyPorori.transaction.domain.Transaction;
import HeyPorori.transaction.domain.TransactionAttach;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRes {
    private String title;
    private String imageName;
    private String address;
    private int recommend;

    public static PostRes toDto(Transaction transaction, String imageName){
        return PostRes.builder()
                .title(transaction.getTitle())
                .imageName(imageName)
                .address(transaction.getAddress())
                .recommend(transaction.getRecommendList().size())
                .build();
    }
}
