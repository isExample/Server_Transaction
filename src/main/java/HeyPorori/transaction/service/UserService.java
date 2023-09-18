package HeyPorori.transaction.service;

import HeyPorori.transaction.config.BaseException;
import HeyPorori.transaction.config.BaseResponse;
import HeyPorori.transaction.config.BaseResponseStatus;
import HeyPorori.transaction.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;
    private static final String LOG_FORMAT = "Method : {}";

    public Long getUserId(String token) {
        return postTokenRequest(token).getUserId();
    }

    private UserInfo postTokenRequest(String token) {
        return webClient.get()
                .uri("/token/me")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono((new ParameterizedTypeReference<BaseResponse<UserInfo>>() {
                }))
                .map(BaseResponse::getData)
                .block();
    }

    public void sendTestJwtRequest(String token) {
        try {
            webClient.post()
                    .uri("/test/jwt")
                    .header("Authorization", token)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            log.warn(LOG_FORMAT, "sendTestJwtRequest");
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }
    }
}
