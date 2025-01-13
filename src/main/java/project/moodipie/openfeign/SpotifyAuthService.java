package project.moodipie.openfeign;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class SpotifyAuthService {

    private final SpotifyAuthClient spotifyAuthClient;
    private final SpotifyProperties spotifyProperties; // Spotify 클라이언트 시크릿


    public String getAccessToken() {
        String authorizationHeader = SpotifyAuthUtil.encodeClientCredentials(spotifyProperties.getClientId(), spotifyProperties.getClientSecret());
        SpotifyTokenResponse response = spotifyAuthClient.getToken(authorizationHeader, "client_credentials");
        return response.getAccess_token();
    }
}
