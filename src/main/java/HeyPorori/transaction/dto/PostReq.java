package HeyPorori.transaction.dto;

import HeyPorori.transaction.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostReq {
    @Size(min = 1, max = 20, message = "거래 게시글 제목의 길이는 1 이상 20 이하입니다.")
    private String title;
    @Size(min = 1, max = 200, message = "거래 게시글 내용의 길이는 1 이상 200 이하입니다.")
    private String content;
    @Pattern(regexp = "^(전자제품|의류 및 액세서리|가구 및 가정용품|스포츠 및 레저|자동차 및 오토바이|도서 및 음악|아기 및 어린이 용품|기타)$", message = "유효하지 않은 카테고리입니다.")
    private String category;
    @Size(max = 3, message = "거래 게시글에 첨부할 수 있는 사진은 3장 이하입니다.")
    private List<String> imageNameList;
}
