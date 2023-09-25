package HeyPorori.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPostReq {
    private Long transactionId;
    @Pattern(regexp = "^(INACTIVE|ACTIVE)$", message = "유효하지 않은 상태입니다.")
    private String toStatus;
    @Pattern(regexp = "^(INACTIVE|ACTIVE)$", message = "유효하지 않은 상태입니다.")
    private String fromStatus;
}