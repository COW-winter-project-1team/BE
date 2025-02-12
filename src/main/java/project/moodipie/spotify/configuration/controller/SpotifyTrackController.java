package project.moodipie.spotify.configuration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.service.TrackService;
import project.moodipie.response.ApiRes;
import project.moodipie.spotify.configuration.controller.dto.request.SpotifyTrackRequest;
import project.moodipie.spotify.configuration.service.SpotifyTrackService;

import java.util.List;

@RestController
@RequestMapping("/api/spotify/api")
@RequiredArgsConstructor
@Tag(name = "스포티파이 api", description = "스포티파이 외부 api")
public class SpotifyTrackController {

    private final SpotifyTrackService spotifyTrackService;
    private final TrackService trackService;

    @Operation(summary = "외부 api로 노래 저장", description = "노래를 가져와 db에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @PostMapping("tracks")
    public ResponseEntity<ApiRes<List<CreateTrackRequest>>> saveTracks(@RequestBody @Valid List<SpotifyTrackRequest> request) throws JsonProcessingException {
        List<CreateTrackRequest> tracks = spotifyTrackService.createTrackRequests(request);
        trackService.save(tracks);
        ApiRes<List<CreateTrackRequest>> response = ApiRes.ok(tracks);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
