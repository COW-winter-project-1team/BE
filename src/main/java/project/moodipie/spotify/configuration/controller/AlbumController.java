package project.moodipie.spotify.configuration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.service.TrackService;
import project.moodipie.spotify.configuration.service.SpotifyTrackService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/spotify/api")
@RequiredArgsConstructor
public class AlbumController {

    private final SpotifyTrackService spotifyTrackService;
    private final TrackService trackService;


    @GetMapping("/tracks/{name}/{artist}")
    public JsonNode helloWorld2(@PathVariable("name") String name,       // 곡 제목
                                @PathVariable("artist") String artist) {

        return spotifyTrackService.SearchTrack(name,artist);
    }

    @PostMapping("/tracks")
    public void getTracks(String ids) throws JsonProcessingException {
        List<CreateTrackRequest> tracks = spotifyTrackService.getTracks(ids);
        trackService.save(tracks);
    }

    @PostMapping("tracks/{name1}/{artirst1}/{name2}/{artirst2}")
    public String SearchTracks(@RequestParam("name1")String name1,@RequestParam("artirst1")String artirst1,
                             @RequestParam("name2")String name2,@RequestParam("artirst2")String artirst2)throws JsonProcessingException{
        List<String> trackNames = Arrays.asList(name1, name2);
        List<String> artistNames = Arrays.asList(artirst1,artirst2);
        return spotifyTrackService.SearchTracks(trackNames,artistNames);
    }
}
