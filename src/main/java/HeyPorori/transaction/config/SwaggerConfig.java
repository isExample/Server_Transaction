package HeyPorori.transaction.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("중고거래 서비스 API")
                        .version("1.7.0")
                        .description("[한이음 ICT 멘토링 공모전 프로젝트] 클라우드를 활용한 지역 기반 거래 플랫폼 - 중고거래 서비스"));
    }
}