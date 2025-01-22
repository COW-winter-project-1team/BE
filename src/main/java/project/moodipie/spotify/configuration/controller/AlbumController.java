package project.moodipie.spotify.configuration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.service.TrackService;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.service.SpotifyTrackService;

import java.util.List;

@RestController
@RequestMapping("/spotify/api")
@RequiredArgsConstructor
public class AlbumController {


    private final SpotifyTrackService spotifyTrackService;
    private final TrackService trackService;


    @GetMapping("/tracks/{name}/{artist}")
    public ResponseEntity<Track> helloWorld2(@PathVariable("name") String name,       // 곡 제목
                                             @PathVariable("artist") String artist) {

        return spotifyTrackService.SearchTrack(name,artist);
    }

    @PostMapping("/tracks")
    public void getTracks(String ids) throws JsonProcessingException {
        List<CreateTrackRequest> tracks = spotifyTrackService.getTracks(ids);
        trackService.save(tracks);
    }
}
