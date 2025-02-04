package project.moodipie.music.playlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.request.UpdatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.controller.dto.response.PlaylistTrackResponse;
import project.moodipie.music.playlist.service.PlaylistService;
import project.moodipie.user.controller.dto.SessionUser;
import project.moodipie.user.handler.exeption.RestfullException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlists")
@Tag(name = "플레이리스트", description = "플레이리스트 CRUD")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final HttpSession session;

    @Operation(summary = "플레이리스트 생성", description = "새로운 플레이리스트를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
    })
    @PostMapping
    public void savePlaylist(@RequestBody CreatePlaylistRequest request) {
        playlistService.savePlaylist(request);
    }

    @Operation(summary = "플레이리스트 정보 조회", description = "생성된 플레이리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("/{id}")
    public PlaylistTrackResponse playlistResponse(@PathVariable("id") Long id) {
        return playlistService.findPlaylistById(id);
    }

    @Operation(summary = "플레이리스트 정보 수정", description = "생성된 플레이리스트를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    @PutMapping("/{id}")
    public void updatePlaylist(@PathVariable("id") Long id,
                               @RequestBody UpdatePlaylistRequest updatePlaylistRequest) {
        playlistService.updatePlaylist(id, updatePlaylistRequest);
    }

    @Operation(summary = "플레이리스트 내부 트랙 삭제", description = "플레이리스트 내부의 트랙을 삭제 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
    })
    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public void deletePlaylistTrack(@PathVariable("playlistId") Long playlistId,
                                    @PathVariable("trackId") Long trackId) {
        playlistService.deletePlaylistTrack(playlistId, trackId);
    }

    @Operation(summary = "홈에서 플레이리스트 조회", description = "홈 화면에서 플레이리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> findAllPlaylist() {
        SessionUser currentUser = getSessionUser();
        List<PlaylistResponse> allPlaylist = playlistService.findAllPlaylist(currentUser.getId());
        return ResponseEntity.ok(allPlaylist);
    }

    @Operation(summary = "플레이리스트 삭제", description = "홈 화면에서 플레이리스트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @DeleteMapping("/{playlistId}")
    public void deletePlaylist(@PathVariable("playlistId") Long playlistId) {
        playlistService.deletePlaylist(playlistId);
    }

    private SessionUser getSessionUser() {
        SessionUser currentUser = (SessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new RestfullException("User is not logged in.", HttpStatus.BAD_REQUEST);
        }
        return currentUser;
    }
}
