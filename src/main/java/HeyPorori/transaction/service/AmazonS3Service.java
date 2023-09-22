package HeyPorori.transaction.service;

import HeyPorori.transaction.dto.PreSignedUrlRes;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AmazonS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    public PreSignedUrlRes getPreSignedUrl(){
        String uuid = UUID.randomUUID().toString();
        String objectKey = "transactions/"+uuid;

        GeneratePresignedUrlRequest request = generatePresignedUrlRequest(bucket, objectKey);
        PreSignedUrlRes response = PreSignedUrlRes.builder()
                .url(amazonS3.generatePresignedUrl(request).toString())
                .build();
        return response;
    }

    private GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucket, String imageName){
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 5; // 5분
        expiration.setTime(expTimeMillis);

        //Pre-Signed Url request 생성
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, imageName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        //request 파라미터 추가
        request.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        return request;
    }
}