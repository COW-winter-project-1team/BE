package project.moodipie.spotify.configuration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.service.TrackService;

//@RestController
@RequestMapping("/spotify/api")
@RequiredArgsConstructor
public class AlbumController {


    private final TrackService trackService;


    @GetMapping("/tracks/{name}/{artist}")
    public ResponseEntity<Track> helloWorld2(@PathVariable("name") String name,       // 곡 제목
                                             @PathVariable("artist") String artist) {

        return trackService.SearchTrack(name,artist);
    }

    @GetMapping("/tracks")
    public ArrayNode getTracks(String ids) throws JsonProcessingException {
        return trackService.getTracks(ids);
    }
}
