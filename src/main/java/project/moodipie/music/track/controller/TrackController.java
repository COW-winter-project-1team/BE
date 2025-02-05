package project.moodipie.music.track.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.controller.dto.response.TrackResponse;
import project.moodipie.music.track.service.TrackService;

import java.util.List;

@Tag(name = "음악 CRUD", description = "스포티파이에서 가져온 음악정보")
@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @Operation(summary = "음악저장", description = "가져온 음악을 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @PostMapping
    public void createTracks(@RequestBody List<CreateTrackRequest> createTrackRequest) {
        trackService.save(createTrackRequest);
    }

    @Operation(summary = "음악조회", description = "가져온 음악을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @GetMapping("/{trackId}")
    public TrackResponse getTrack(@Parameter(description = "트랙 정보 얻기 위한 트랙 아이디", required = true)
                                  @PathVariable String trackId) {
        return trackService.getTrack(trackId);
    }
}
