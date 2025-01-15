package project.moodipie.spotify.configuration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.spotify.configuration.client.*;

import java.util.List;

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

    @GetMapping("/tracks/{genre}")
    public ResponseEntity<List<Track>> helloWorld2(@PathVariable("genre") String genre) {
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String query = "genre:" + genre;
        String token = authSpotifyClient.login(request).getAccessToken();
        TrackResponse response = trackSpotifyClient.getTrack("Bearer " + token, query,"track");
        return ResponseEntity.ok(response.getTracks().getItems());
    }
}
