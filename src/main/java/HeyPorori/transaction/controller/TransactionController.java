package HeyPorori.transaction.controller;

import HeyPorori.transaction.config.BaseException;
import HeyPorori.transaction.config.BaseResponse;
import HeyPorori.transaction.config.BaseResponseStatus;
import HeyPorori.transaction.dto.CreatePostReq;
import HeyPorori.transaction.dto.PostDetailRes;
import HeyPorori.transaction.dto.PostsRes;
import HeyPorori.transaction.dto.PreSignedUrlRes;
import HeyPorori.transaction.service.AmazonS3Service;
import HeyPorori.transaction.service.TransactionService;
import HeyPorori.transaction.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Tag(name = "중고거래", description = "중고거래 관련 API 입니다.")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;
    private final AmazonS3Service amazonS3Service;

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
    public BaseResponse<BaseResponseStatus> createPost(@RequestHeader("Authorization") String token, @RequestBody @Valid CreatePostReq postReq) {
        transactionService.createPost(token, postReq);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "Pre-Signed Url 발급 API", description = "AWS S3 이미지 업로드 권한을 요청하기 위한 API입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @GetMapping("/url")
    public BaseResponse<PreSignedUrlRes> getPreSignedUrl(@RequestHeader("Authorization") String token) throws BaseException {
        return new BaseResponse<>(amazonS3Service.getPreSignedUrl());
    }

    @Operation(summary = "중고거래 게시글 목록 조회 API", description = "중고거래 서비스의 거래 게시글 목록을 조회하기 위한 API입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @GetMapping("/post")
    public BaseResponse<List<PostsRes>> getAllPostByCategory(String category) throws BaseException {
        return new BaseResponse<>(transactionService.findAllPostByCategory(category));
    }

    @Operation(summary = "중고거래 게시글 상세 조회 API", description = "중고거래 서비스의 거래 게시글 상세정보를 조회하기 위한 API입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @GetMapping("/post/{transactionId}")
    public BaseResponse<PostDetailRes> getPostDetail(@PathVariable Long transactionId) throws BaseException {
        return new BaseResponse<>(transactionService.getPostDetail(transactionId));
    }

    @Operation(summary = "중고거래 게시글 삭제 API", description = "중고거래 서비스의 거래 게시글을 삭제하기 위한 API입니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))
    @DeleteMapping("/post/{transactionId}")
    public BaseResponse<String> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long transactionId) throws BaseException {
        transactionService.deletePost(token, transactionId);
        return new BaseResponse<>("게시글이 삭제되었습니다.");
    }
}
