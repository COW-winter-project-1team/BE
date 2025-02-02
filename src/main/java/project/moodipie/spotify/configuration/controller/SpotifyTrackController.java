package project.moodipie.spotify.configuration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "스포티파이 api", description = "스포티파이 외부 api")
public class SpotifyTrackController {

    private final SpotifyTrackService spotifyTrackService;
    private final TrackService trackService;

    @Operation(summary = "외부 api로 노래 저장", description = "노래를 가져와 db에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @PostMapping("tracks/{name1}/{artist1}/{name2}/{artist2}")
    public void saveTracks(@PathVariable String artist1, @PathVariable String artist2, @PathVariable String name1, @PathVariable String name2) throws JsonProcessingException {
        List<String> trackNames = Arrays.asList(name1, name2);
        List<String> artistNames = Arrays.asList(artist1, artist2);
        List<CreateTrackRequest> tracks = spotifyTrackService.searchTracks(trackNames, artistNames);
        trackService.save(tracks);
    }
}
