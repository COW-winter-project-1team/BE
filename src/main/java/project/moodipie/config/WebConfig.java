package project.moodipie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Spring Security가 처리하기 전에 CORS가 먼저 적용됩니다.
        registry.addMapping("/**")  // 모든 엔드포인트에 대해 CORS 적용
                .allowedOrigins("http://localhost:5173")  // 허용할 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메소드
                .allowedHeaders("Content-Type", "Authorization")  // 허용할 헤더
                .allowCredentials(true);  // 쿠키와 자격 증명 포함
    }
}