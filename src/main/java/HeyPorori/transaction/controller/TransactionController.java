package HeyPorori.transaction.controller;

import HeyPorori.transaction.config.BaseException;
import HeyPorori.transaction.config.BaseResponse;
import HeyPorori.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "중고거래", description = "중고거래 관련 API 입니다.")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Swagger UI 테스트용 메서드", description = "프로젝트 초기 Swagger UI 정상작동을 확인하기 위한 메서드입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @GetMapping("/test")
    public BaseResponse<String> testSwagger() {
        return new BaseResponse<>("테스트 성공");
    }
}
