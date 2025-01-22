package project.moodipie.spotify.configuration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.client.Auth.AuthSpotifyClient;
import project.moodipie.spotify.configuration.client.Auth.LoginRequest;

import java.util.List;
import java.util.Optional;

//@Service
public class TrackService {
    private final AuthSpotifyClient authSpotifyClient;
    private final AlbumSpotifyClient albumSpotifyClient;
    private final TrackSpotifyClient trackSpotifyClient;
    private final ObjectMapper objectMapper;



    public TrackService(AuthSpotifyClient authSpotifyClient, AlbumSpotifyClient albumSpotifyClient, TrackSpotifyClient trackSpotifyClient, ObjectMapper objectMapper) {
        this.authSpotifyClient = authSpotifyClient;
        this.albumSpotifyClient = albumSpotifyClient;
        this.trackSpotifyClient = trackSpotifyClient;
        this.objectMapper = objectMapper;
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

    public ArrayNode getTracks(String ids) throws JsonProcessingException {
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String token = authSpotifyClient.login(request).getAccessToken();
        JsonNode response = trackSpotifyClient.getTracks("Bearer " + token, ids);
        System.out.println("response = " + response);
        JsonNode tracksNode = response.path("tracks");

        ArrayNode resultArrayNode = objectMapper.createArrayNode();
        for (JsonNode trackNode : tracksNode) {
            String trackId = trackNode.path("id").asText();
            String trackName = trackNode.path("name").asText();
            String artistName = trackNode.path("artists").get(0).path("name").asText();
            String imageUrl = trackNode.path("album").path("images").get(0).path("url").asText();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("trackId", trackId);
            objectNode.put("trackName", trackName);
            objectNode.put("artistName", artistName);
            objectNode.put("imageUrl", imageUrl);

            resultArrayNode.add(objectNode);
        }

        return resultArrayNode;

//        try {
//            return objectMapper.writeValueAsString(resultArrayNode);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error converting response to JSON";
//        }

    }
}
