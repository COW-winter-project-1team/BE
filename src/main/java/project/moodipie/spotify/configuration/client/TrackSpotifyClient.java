package project.moodipie.spotify.configuration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import project.moodipie.spotify.configuration.client.track.TrackResponseForTrack;

@FeignClient(
        name = "AlbumSpotifyClient",
        url = "https://api.spotify.com"
)
public interface TrackSpotifyClient {
    @GetMapping(value = "/v1/search", produces = MediaType.APPLICATION_JSON_VALUE)
    TrackResponse searchTrack(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("q") String query,
            @RequestParam("type") String type);


    @GetMapping(value = "/v1/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    TrackResponseForTrack getTracks(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("ids") String query
    );
}
