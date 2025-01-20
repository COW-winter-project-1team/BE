package project.moodipie.spotify.configuration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.client.track.GetTrack;
import project.moodipie.spotify.configuration.service.TrackService;

import java.util.List;

@RestController
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
    public ResponseEntity<List<GetTrack>> getTracks(String ids){
        return trackService.getTracks(ids);
    }
}
