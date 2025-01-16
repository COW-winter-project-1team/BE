package project.moodipie.spotify.configuration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.spotify.configuration.client.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spotify/api")
public class AlbumController {

    private final AuthSpotifyClient authSpotifyClient;
    private final AlbumSpotifyClient albumSpotifyClient;
    private final TrackSpotifyClient trackSpotifyClient;

    public AlbumController(AuthSpotifyClient authSpotifyClient, AlbumSpotifyClient albumSpotifyClient, TrackSpotifyClient trackSpotifyClient) {
        this.authSpotifyClient = authSpotifyClient;
        this.albumSpotifyClient = albumSpotifyClient;
        this.trackSpotifyClient = trackSpotifyClient;
    }

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> helloWorld() {
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String token = authSpotifyClient.login(request).getAccessToken();
        AlbumResponse response = albumSpotifyClient.getReleases("Bearer " + token);
        return ResponseEntity.ok(response.getAlbums().getItems());
    }

    @GetMapping("/tracks/{name}/{artist}")
    public ResponseEntity<Track> helloWorld2(@PathVariable("name") String name,       // 곡 제목
                                             @PathVariable("artist") String artist) {
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String query = String.format("track:%s artist:%s", name, artist);
        String token = authSpotifyClient.login(request).getAccessToken();
        TrackResponse response = trackSpotifyClient.getTrack("Bearer " + token, query, "track");
        Optional<Track> exactMatch = response.getTracks().getItems().stream()
                .filter(track -> track.getAlbum().getName().equalsIgnoreCase(name))
                .filter(track -> track.getAlbum().getArtists().stream()
                        .anyMatch(a -> a.getName().equalsIgnoreCase(artist)))
                .findFirst();

        // 5. 결과 반환
        return exactMatch
                .map(ResponseEntity::ok) // 정확한 결과 반환
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
