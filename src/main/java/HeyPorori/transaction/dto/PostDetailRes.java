package HeyPorori.transaction.dto;

import HeyPorori.transaction.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailRes {
    private Long transactionId;
    private String title;
    private String content;
    private String nickName;
    private String address;
    private String createdAt;
    private int recommend;
    private List<String> imageNameList;
    private boolean isOwner;

    public static PostDetailRes toDto(Transaction transaction, String nickName, String createdAt, List<String> imageNameList, boolean isOwner){
        return PostDetailRes.builder()
                .transactionId(transaction.getTransactionId())
                .title(transaction.getTitle())
                .content(transaction.getContent())
                .nickName(nickName)
                .address(transaction.getAddress())
                .createdAt(createdAt)
                .recommend(transaction.getRecommendList().size())
                .imageNameList(imageNameList)
                .isOwner(isOwner)
                .build();
    }
}
