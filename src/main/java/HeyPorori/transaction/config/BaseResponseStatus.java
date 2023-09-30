package HeyPorori.transaction.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /* 요청 성공 */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /* 요청 실패 */
    USER_INVALID_RESPONSE(false, 1050, "User 서비스로부터 올바른 응답을 받지 못하였습니다."),
    INVALID_JWT(false, 1051, "잘못된 JWT 토큰입니다."),
    INVALID_CATEGORY(false, 3001, "유효하지 않은 카테고리입니다."),
    POST_NOT_FOUND(false, 3010, "존재하지 않는 게시글입니다."),
    INVALID_POST_OWNER(false, 3011, "게시글 작성자가 아닙니다.")

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