package project.moodipie.spotify.configuration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.service.TrackService;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.spotify.configuration.controller.dto.request.SpotifyTrackRequest;
import project.moodipie.spotify.configuration.service.SpotifyTrackService;
import project.moodipie.swagger.ApiExceptionExplanation;
import project.moodipie.swagger.ApiResponseExplanations;

import java.util.List;

@RestController
@RequestMapping("/spotify/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@Tag(name = "스포티파이 api", description = "스포티파이 외부 api")
public class SpotifyTrackController {

    private final SpotifyTrackService spotifyTrackService;
    private final TrackService trackService;

    @Operation(summary = "외부 api로 노래 저장", description = "노래를 가져와 db에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "저장 실패- trackId NULL", description = "잘못된 TrackId로 조회를 시도하여 트랙 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
                    @ApiExceptionExplanation(name = "생성 실패 - 스포티파이 오류", description = "스포티파이 오류로 플레이리스트가 생성되지 않았습니다.", value = ErrorCode.class, constant = "SPOTIFY_ERROR"),
            }
    )
    @PostMapping("tracks")
    public ResponseEntity<ApiRes<List<CreateTrackRequest>>> saveTracks(@RequestBody @Valid List<SpotifyTrackRequest> request) throws JsonProcessingException {
        List<CreateTrackRequest> tracks = spotifyTrackService.createTrackRequests(request);
        trackService.save(tracks);
        ApiRes<List<CreateTrackRequest>> response = ApiRes.ok(tracks);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    //외부 api 다루는 컨트롤러에만 예외가 일어나기 때문에 글로벌에서 옮겼습니다.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(FeignException.BadRequest.class)
    protected ResponseEntity<ApiRes<?>> handleFeignException(FeignException.BadRequest exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.SPOTIFY_ERROR);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }
}
