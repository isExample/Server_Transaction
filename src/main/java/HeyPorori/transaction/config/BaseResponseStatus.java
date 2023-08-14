package HeyPorori.transaction.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /* 요청 성공 */
    SUCCESS(true, 1000, "요청에 성공하였습니다.")
    ;

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int statusCode, String message) {
        this.isSuccess = isSuccess;
        this.statusCode = statusCode;
        this.message = message;
    }
}