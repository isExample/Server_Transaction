package HeyPorori.transaction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PreSignedUrlRes {
    private String url;

    @Builder
    public PreSignedUrlRes(String url) {
        this.url = url;
    }
}
