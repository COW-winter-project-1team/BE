package project.moodipie.music.track.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.moodipie.music.track.controller.dto.response.TrackResponse;
import project.moodipie.music.track.service.TrackService;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.swagger.ApiExceptionExplanation;
import project.moodipie.swagger.ApiResponseExplanations;

@Tag(name = "음악 CRUD", description = "스포티파이에서 가져온 음악정보")
@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TrackController {

    private final TrackService trackService;

    @Operation(summary = "음악조회", description = "가져온 음악을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "조회 실패- trackId NULL", description = "잘못된 TrackId로 조회를 시도하여 트랙 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @GetMapping("/{trackId}")
    public ResponseEntity<ApiRes<TrackResponse>> getTrack(@Parameter(description = "트랙 정보 얻기 위한 트랙 아이디", required = true)
                                  @PathVariable("trackId") String trackId) {

        TrackResponse track = trackService.getTrack(trackId);
        ApiRes<TrackResponse> response = ApiRes.ok(track);
        int httpStatus = response.getHttpStatus();
        System.out.println(httpStatus);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
