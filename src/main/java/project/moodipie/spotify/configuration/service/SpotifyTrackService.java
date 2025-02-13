package project.moodipie.spotify.configuration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.client.Auth.AuthSpotifyClient;
import project.moodipie.spotify.configuration.client.Auth.SpotifyLoginRequest;
import project.moodipie.spotify.configuration.client.SpotifyYml;
import project.moodipie.spotify.configuration.controller.dto.request.SpotifyTrackRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyTrackService {
    private static final Logger log = LoggerFactory.getLogger(SpotifyTrackService.class);
    private final AuthSpotifyClient authSpotifyClient;
    private final TrackSpotifyClient trackSpotifyClient;
    private final ObjectMapper objectMapper;
    private final SpotifyYml spotifyYml;


    public List<CreateTrackRequest> createTrackRequests(List<SpotifyTrackRequest> request) throws JsonProcessingException {
        List<String> trackNames  = new ArrayList<>();
        List<String> artistNames = new ArrayList<>() ;
        for (SpotifyTrackRequest spotifyTrackRequest : request) {
            if (spotifyTrackRequest.getTrackName().isBlank() || spotifyTrackRequest.getArtistName().isBlank()) {
                continue;
            }
            trackNames.add(spotifyTrackRequest.getTrackName());
            artistNames.add(spotifyTrackRequest.getArtistName());

        }
        return searchTracks(trackNames, artistNames);
    }

    private List<CreateTrackRequest> searchTracks(List<String> trackNames, List<String> artistNames) throws JsonProcessingException {
        if (trackNames.size() != artistNames.size()) {
            throw new IllegalArgumentException("Track names and artist names must have the same size.");}
        String ids = "";
        for (int i = 0; i < trackNames.size(); i++) {
            String name = trackNames.get(i);
            String artist = artistNames.get(i);
            JsonNode trackJson = searchTrackAtSpotify(name, artist);
            String id = trackJson.path("id").asText();
            if (!id.isEmpty()) {
                ids += id + ",";
            }
        }
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        return getTracks(ids);
    }

    private JsonNode searchTrackAtSpotify(String name, String artist) {
        String query = String.format("track:%s artist:%s", name, artist);
        JsonNode response = trackSpotifyClient.searchTrack("Bearer " + getToken(), query, "track");

        JsonNode jsonsNode = response.path("tracks");
        ObjectNode arrayNode = objectMapper.createObjectNode();

        JsonNode itemsNode = jsonsNode.path("items");

        // 검색 결과가 없으면 그냥 빈 JSON 반환
        if (itemsNode.isMissingNode() || !itemsNode.isArray() || itemsNode.isEmpty()) {
            log.info("아티스트나, 노래제목이 달라 노래 저장을 한 개 건너뜁니다.");
            return arrayNode;  // 그냥 빈 JSON 반환
        }

        String id = itemsNode.get(0).path("id").asText();
        return arrayNode.put("id", id);
    }


    private List<CreateTrackRequest> getTracks(String ids) throws JsonProcessingException {
        JsonNode response = trackSpotifyClient.getTracks("Bearer " + getToken(), ids);
        ArrayNode resultArrayNode = getJsonNodes(response);

        return objectMapper.readValue(resultArrayNode.toString(), new TypeReference<>() {});

    }



    private ArrayNode getJsonNodes(JsonNode response) {
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
    }

    private String getToken() {
        SpotifyLoginRequest request = new SpotifyLoginRequest(
                spotifyYml.getGrantType(),
                spotifyYml.getClientId(),
                spotifyYml.getClientSecret()
        );
        return authSpotifyClient.login(request).getAccessToken();
    }
}
