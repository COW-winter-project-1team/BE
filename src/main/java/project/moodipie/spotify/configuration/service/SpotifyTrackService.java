package project.moodipie.spotify.configuration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.client.Auth.AuthSpotifyClient;
import project.moodipie.spotify.configuration.client.Auth.LoginRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotifyTrackService {
    private final AuthSpotifyClient authSpotifyClient;
    private final TrackSpotifyClient trackSpotifyClient;
    private final ObjectMapper objectMapper;

    public String SearchTracks(List<String> trackNames, List<String> artistNames) throws JsonProcessingException {
        if (trackNames.size() != artistNames.size()) {
            throw new IllegalArgumentException("Track names and artist names must have the same size.");}
        String ids = "";
        for (int i = 0; i < trackNames.size(); i++) {
            String name = trackNames.get(i);
            String artist = artistNames.get(i);
            JsonNode trackJson = SearchTrack(name, artist);
            String id = trackJson.path("id").asText();
            if (!id.isEmpty()) {
                ids += id + ",";
            }
        }
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        System.out.println(ids);
        return ids;     //return getTracks(ids);
    }

    public JsonNode SearchTrack(String name, String artist) {
        String query = String.format("track:%s artist:%s", name, artist);
        System.out.println("Encoded Query: " + query);
        JsonNode response = trackSpotifyClient.searchTrack("Bearer " + getToken(), query, "track");
        System.out.println("response = " + response);

        JsonNode jsonsNode = response.path("tracks");

        ObjectNode arrayNode = objectMapper.createObjectNode();

        String id = jsonsNode.path("items").get(0).path("id").asText();

        ObjectNode id1 = arrayNode.put("id", id);

        return id1;

    }

    public List<CreateTrackRequest> getTracks(String ids) throws JsonProcessingException {
        JsonNode response = trackSpotifyClient.getTracks("Bearer " + getToken(), ids);
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

    private String getToken() {
        LoginRequest request = new LoginRequest(
                "client_credentials",
                "97bc0e5f8320421eaf8f9383ae3399be",
                "f7e81d2bcfd04c9393925e0bfc4493f9"
        );
        return authSpotifyClient.login(request).getAccessToken();
    }
}
