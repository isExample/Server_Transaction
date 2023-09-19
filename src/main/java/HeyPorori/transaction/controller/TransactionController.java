package HeyPorori.transaction.controller;

import HeyPorori.transaction.config.BaseException;
import HeyPorori.transaction.config.BaseResponse;
import HeyPorori.transaction.config.BaseResponseStatus;
import HeyPorori.transaction.dto.PostReq;
import HeyPorori.transaction.service.TransactionService;
import HeyPorori.transaction.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Tag(name = "중고거래", description = "중고거래 관련 API 입니다.")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    // 테스트용 APIs
    @Operation(summary = "Swagger UI 테스트용 메서드", description = "프로젝트 초기 Swagger UI 정상작동을 확인하기 위한 메서드입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @GetMapping("/test")
    public BaseResponse<String> testSwagger() { return new BaseResponse<>("테스트 성공"); }

    @Operation(summary = "User 서버 테스트용 메서드", description = "User 서버와의 통신이 정상작동하는지 확인하기 위한 메서드입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @PostMapping("/testUser")
    public BaseResponse<Long> testUserService(@RequestHeader("Authorization") String token) {
        userService.sendTestJwtRequest(token);
        return new BaseResponse<>(userService.getUserId(token));
    }

    // 실사용 APIs
    @Operation(summary = "중고거래 게시글 작성 API", description = "중고거래 서비스의 거래 게시글을 작성하기 위한 API입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @PostMapping("/post")
    public BaseResponse<BaseResponseStatus> createPost(@RequestHeader("Authorization") String token, @RequestBody @Valid PostReq postReq) {
        transactionService.createPost(token, postReq);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }
}
