package project.moodipie.music.playlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.request.UpdatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.controller.dto.response.PlaylistTrackEachResponse;
import project.moodipie.music.playlist.controller.dto.response.PlaylistTrackResponse;
import project.moodipie.music.playlist.service.PlaylistService;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.swagger.ApiExceptionExplanation;
import project.moodipie.swagger.ApiResponseExplanations;
import project.moodipie.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlists")
@SecurityRequirement(name = "Authorization")
@Tag(name = "플레이리스트 CRUD", description = "플레이리스트 CRUD")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    @Operation(summary = "플레이리스트 생성", description = "새로운 플레이리스트를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "생성 실패 - 스포티파이 오류", description = "스포티파이 오류로 플레이리스트가 생성되지 않았습니다.", value = ErrorCode.class, constant = "SPOTIFY_ERROR"),
                    @ApiExceptionExplanation(name = "생성 실패 - 플레이리스트 NULL", description = "요청값이 없어 플레이리스트가 생성되지 않았습니다.", value = ErrorCode.class, constant = "INVALID_FORMAT")
            }
    )
    @PostMapping
    public ResponseEntity<ApiRes<PlaylistResponse>> savePlaylist(
            @AuthenticationPrincipal String userEmail,
            @RequestBody @Valid  CreatePlaylistRequest request) {
        PlaylistResponse createPlaylistRequest = playlistService.savePlaylist(userEmail, request);
        ApiRes<PlaylistResponse> response = ApiRes.created(createPlaylistRequest);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "플레이리스트 정보 조회", description = "생성된 플레이리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "조회 실패 - playlistNumber NULL", description = "잘못된 playlistNumber로 조회하여 플레이리스트 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE")
            }
    )
    @GetMapping("/{playlistNumber}")
    public ResponseEntity<ApiRes<PlaylistTrackResponse>> playlistResponse(
            @Parameter(description = "플레이리스트 정보 얻기 윈한 플레이리스트 아이디", required = true)
            @AuthenticationPrincipal String userEmail,
            @PathVariable("playlistNumber") Long playlistNumber) {
        PlaylistTrackResponse playlistByUserIdAndPlaylistNumber = playlistService.findPlaylistByUserIdAndPlaylistNumber(userEmail, playlistNumber);
        ApiRes<PlaylistTrackResponse> response = ApiRes.ok(playlistByUserIdAndPlaylistNumber);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "플레이리스트 정보 수정", description = "생성된 플레이리스트를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "수정 실패 - playlistNumber NULL", description = "잘못된 playlistNumber로 수정을 시도하여 플레이리스트 수정에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
                    @ApiExceptionExplanation(name = "수정 실패 - 필드 에러", description = "필드 조건에 맞지 않아 플레이리스트 수정에 실패했습니다.", value = ErrorCode.class, constant = "INVALID_FORMAT")
            }
    )
    @PutMapping("/{playlistNumber}")
    public ResponseEntity<ApiRes<UpdatePlaylistRequest>> updatePlaylist(
            @AuthenticationPrincipal String userEmail,
            @PathVariable("playlistNumber") Long playlistNumber,
            @Valid @RequestBody UpdatePlaylistRequest updatePlaylistRequest) {
        UpdatePlaylistRequest updatePlaylistData = playlistService.updatePlaylist(userEmail, playlistNumber, updatePlaylistRequest);
        ApiRes<UpdatePlaylistRequest> response = ApiRes.update(updatePlaylistData);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "플레이리스트 내부 트랙 삭제", description = "플레이리스트 내부의 트랙을 삭제 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "삭제 실패 - playlistNumber or trackId NULL", description = "잘못된 playlistNumber 나 TrackId로 삭제를 시도하여 플레이리스트 삭제에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @DeleteMapping("/{playlistNumber}/tracks/{playlistTrackNumber}")
    public ResponseEntity<ApiRes<PlaylistTrackEachResponse>> deletePlaylistTrack(
            @AuthenticationPrincipal String userEmail,
            @Parameter(description = "선택할 플레이리스트 아이디", required = true)
            @PathVariable("playlistNumber") Long playlistNumber,
            @Parameter(description = "삭제할 트랙 번호", required = true)
            @PathVariable("playlistTrackNumber") Long playlistTrackNumber) {
        PlaylistTrackEachResponse playlistTracks = playlistService.deletePlaylistTrack(userEmail, playlistNumber, playlistTrackNumber);
        ApiRes<PlaylistTrackEachResponse> response = ApiRes.delete(playlistTracks);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "홈에서 플레이리스트 조회", description = "홈 화면에서 플레이리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "조회 실패 - userEmail 오류", description = "userEmail 값이 Null 이라서 플레이리스트 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @GetMapping
    public ResponseEntity<ApiRes<List<PlaylistResponse>>> findAllPlaylist(@AuthenticationPrincipal String userEmail) {
        Long userId = userService.findUserByEmail(userEmail).getId();
        List<PlaylistResponse> allPlaylist = playlistService.findAllPlaylistByUserId(userId);
        ApiRes<List<PlaylistResponse>> response = ApiRes.ok(allPlaylist);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "플레이리스트 삭제", description = "홈 화면에서 플레이리스트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "삭제 실패 - playlistNumber NULL", description = "잘못된 playlistNumber 로 삭제를 시도하여 플레이리스트 삭제에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @DeleteMapping("/{playlistNumber}")
    public ResponseEntity<ApiRes<PlaylistResponse>> deletePlaylist(
            @Parameter(description = "삭제할 플레이리스트 넘버", required = true)
            @AuthenticationPrincipal String userEmail,
            @PathVariable("playlistNumber") Long playlistNumber) {
        PlaylistResponse playlist = playlistService.deletePlaylist(userEmail, playlistNumber);
        ApiRes<PlaylistResponse> response = ApiRes.delete(playlist);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}
