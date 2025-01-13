package project.moodipie.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spotifyAuthClient", url = "https://accounts.spotify.com/api")
public interface SpotifyAuthClient {
    @PostMapping("/token")
    SpotifyTokenResponse getToken(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType);
}
