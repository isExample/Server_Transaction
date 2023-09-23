package HeyPorori.transaction.dto;

import HeyPorori.transaction.domain.Transaction;
import HeyPorori.transaction.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoRes {
    private long userId;
    private String nickName;

    public static UserInfoRes toDto(UserInfo userInfo){
        return UserInfoRes.builder()
                .userId(userInfo.getUserId())
                .nickName(userInfo.getNickName())
                .build();
    }
}
