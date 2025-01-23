package project.moodipie.spotify.configuration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.client.Auth.AuthSpotifyClient;
import project.moodipie.spotify.configuration.client.Auth.LoginRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotifyTrackService {
    private final AuthSpotifyClient authSpotifyClient;
    private final TrackSpotifyClient trackSpotifyClient;
    private final ObjectMapper objectMapper;

    public JsonNode SearchTrack(String name, String artist) {
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        String query = String.format("track:%s artist:%s", name, artist);
        System.out.println("Encoded Query: " + query);
        String token = authSpotifyClient.login(request).getAccessToken();
        JsonNode response = trackSpotifyClient.searchTrack("Bearer " + token, query, "track");
        System.out.println("response = " + response);

        JsonNode jsonsNode = response.path("tracks");

        ObjectNode arrayNode = objectMapper.createObjectNode();

        String id = jsonsNode.path("items").get(0).path("id").asText();

        ObjectNode id1 = arrayNode.put("id", id);

        return id1;

    }

    public List<CreateTrackRequest> getTracks(String ids) throws JsonProcessingException {
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

        return objectMapper.readValue(resultArrayNode.toString(), new TypeReference<>() {
        });

    }
}
