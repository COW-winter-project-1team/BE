package project.moodipie.spotify.client_credentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SpotifyAuthService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private final WebClient webClient;

    public SpotifyAuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://accounts.spotify.com")
                .defaultHeader("Authorization", "Basic " + getEncodedCredentials())
                .build();
    }

    // 클라이언트 아이디와 비밀번호를 Base64로 인코딩한 값 반환
    private String getEncodedCredentials() {
        String credentials = clientId + ":" + clientSecret;
        return new String(java.util.Base64.getEncoder().encode(credentials.getBytes()));
    }

    // 액세스 토큰을 요청하는 메서드
    public Mono<String> getAccessToken() {
        return webClient.post()
                .uri("/api/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    // 여기서 JSON 파싱하여 토큰 추출 (실제로는 JSON에서 "access_token" 값을 추출해야 함)
                    return response;  // 실제로는 response에서 access_token을 추출
                });
    }
}
