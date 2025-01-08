package project.moodipie.spotify.client_credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SpotifyService {

    private final WebClient webClient;

    @Autowired
    public SpotifyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.spotify.com/v1")
                .build();
    }

    // 사용자 정보를 가져오는 메서드
    public Mono<String> getUserProfile(String accessToken) {
        return webClient.get()
                .uri("/me")  // /me는 현재 로그인한 사용자의 프로필을 반환
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    return Mono.error(new RuntimeException("Spotify 사용자 정보 조회 실패", e));
                });
    }
}
