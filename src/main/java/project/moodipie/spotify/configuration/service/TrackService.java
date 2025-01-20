package project.moodipie.spotify.configuration.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.client.Auth.AuthSpotifyClient;
import project.moodipie.spotify.configuration.client.Auth.LoginRequest;
import project.moodipie.spotify.configuration.client.track.GetTrack;
import project.moodipie.spotify.configuration.client.track.TrackResponseForTrack;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {
    private final AuthSpotifyClient authSpotifyClient;
    private final AlbumSpotifyClient albumSpotifyClient;
    private final TrackSpotifyClient trackSpotifyClient;

    public TrackService(AuthSpotifyClient authSpotifyClient, AlbumSpotifyClient albumSpotifyClient, TrackSpotifyClient trackSpotifyClient) {
        this.authSpotifyClient = authSpotifyClient;
        this.albumSpotifyClient = albumSpotifyClient;
        this.trackSpotifyClient = trackSpotifyClient;
    }

    public ResponseEntity<List<Album>> getalbum(){
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String token = authSpotifyClient.login(request).getAccessToken();
        AlbumResponse response = albumSpotifyClient.getReleases("Bearer " + token);
        return ResponseEntity.ok(response.getAlbums().getItems());
    }

    public ResponseEntity<Track> SearchTrack(String name, String artist){
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String query = String.format("track:%s artist:%s", name, artist);
        System.out.println("Encoded Query: " + query);
        String token = authSpotifyClient.login(request).getAccessToken();
        TrackResponse response = trackSpotifyClient.searchTrack("Bearer " + token, query, "track");
        System.out.println("response = " + response);
        Optional<Track> exactMatch = response.getTracks().getItems().stream()
                .filter(track -> track.getAlbum().getName().equalsIgnoreCase(name))
                .filter(track -> track.getAlbum().getArtists().stream()
                        .anyMatch(a -> a.getName().equalsIgnoreCase(artist)))
                .findFirst();
        System.out.println("exactMatch = " + exactMatch);
        // 5. 결과 반환
        return exactMatch
                .map(ResponseEntity::ok) // 정확한 결과 반환
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }

    public ResponseEntity<List<GetTrack>> getTracks(String ids) {
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String token = authSpotifyClient.login(request).getAccessToken();
        TrackResponseForTrack response = trackSpotifyClient.getTracks("Bearer " + token, ids);
        System.out.println("response = " + response);
        return ResponseEntity.ok(response.getTracks());
    }
}
