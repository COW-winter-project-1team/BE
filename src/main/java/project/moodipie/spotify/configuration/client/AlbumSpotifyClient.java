package project.moodipie.spotify.configuration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name="AlbumSpotifyClient",
        url = "https://api.spotify.com"
)
public interface AlbumSpotifyClient {

    @GetMapping(value = "/v1/browse/new-releases", produces = MediaType.APPLICATION_JSON_VALUE)
    AlbumResponse getReleases(@RequestHeader("Authorization") String authorization);
}
