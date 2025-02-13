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
import project.moodipie.music.playlist.controller.dto.response.PlaylistTrackResponse;
import project.moodipie.music.playlist.entity.PlaylistTrack;
import project.moodipie.music.playlist.service.PlaylistService;
import project.moodipie.response.ApiRes;
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
            @ApiResponse(responseCode = "200", description = "생성 성공"),
    })
    @PostMapping
    public ResponseEntity<ApiRes<CreatePlaylistRequest>> savePlaylist(
            @AuthenticationPrincipal String userEmail,
            @Valid @RequestBody CreatePlaylistRequest request) {
        CreatePlaylistRequest createPlaylistRequest = playlistService.savePlaylist(userEmail, request);
        ApiRes<CreatePlaylistRequest> response = ApiRes.created(createPlaylistRequest);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "플레이리스트 정보 조회", description = "생성된 플레이리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("/{playlistNumber}")
    public ResponseEntity<ApiRes<PlaylistTrackResponse>> playlistResponse(@Parameter(description = "플레이리스트 정보 얻기 윈한 플레이리스트 아이디", required = true)
                                                  @AuthenticationPrincipal String userEmail,
                                                  @PathVariable("playlistNumber") Long playlistNumber
    ) {
        PlaylistTrackResponse playlistByUserIdAndPlaylistNumber = playlistService.findPlaylistByUserIdAndPlaylistNumber(userEmail, playlistNumber);
        ApiRes<PlaylistTrackResponse> response = ApiRes.ok(playlistByUserIdAndPlaylistNumber);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "플레이리스트 정보 수정", description = "생성된 플레이리스트를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    @PutMapping("/{playlistNumber}")
    public ResponseEntity<ApiRes<UpdatePlaylistRequest>> updatePlaylist(
            @AuthenticationPrincipal String userEmail,
            @PathVariable("playlistNumber") Long playlistNumber,
            @Valid @RequestBody UpdatePlaylistRequest updatePlaylistRequest) {
        UpdatePlaylistRequest updatePlaylistData = playlistService.updatePlaylist(userEmail, playlistNumber, updatePlaylistRequest);
        ApiRes<UpdatePlaylistRequest> response = ApiRes.ok(updatePlaylistData);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "플레이리스트 내부 트랙 삭제", description = "플레이리스트 내부의 트랙을 삭제 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
    })
    @DeleteMapping("/{playlistNumber}/tracks/{trackId}")
    public ResponseEntity<ApiRes<List<PlaylistTrack>>> deletePlaylistTrack(
            @AuthenticationPrincipal String userEmail,
            @Parameter(description = "선택할 플레이리스트 아이디", required = true)
            @PathVariable("playlistNumber") Long playlistNumber,
            @Parameter(description = "삭제할 트랙 번호", required = true)
            @PathVariable("trackId") Long trackId) {
        List<PlaylistTrack> playlistTracks = playlistService.deletePlaylistTrack(userEmail, playlistNumber, trackId);
        ApiRes<List<PlaylistTrack>> response = ApiRes.delete(playlistTracks);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "홈에서 플레이리스트 조회", description = "홈 화면에서 플레이리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
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
    @DeleteMapping("/{playlistNumber}")
    public ResponseEntity<ApiRes<PlaylistResponse>> deletePlaylist(
            @Parameter(description = "삭제할 플레이리스트 넘버", required = true)
            @AuthenticationPrincipal String userEmail,
            @PathVariable("playlistNumber") Long playlistNumber
    ) {
        PlaylistResponse playlist = playlistService.deletePlaylist(userEmail, playlistNumber);
        ApiRes<PlaylistResponse> response = ApiRes.delete(playlist);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}
