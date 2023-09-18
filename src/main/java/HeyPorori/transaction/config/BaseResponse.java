package HeyPorori.transaction.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static HeyPorori.transaction.config.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"statusCode", "message", "data"})
public class BaseResponse<T> {
    private String message;
    private int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse(T data) {
        this.message = SUCCESS.getMessage();
        this.statusCode = SUCCESS.getStatusCode();
        this.data = data;
    }
}