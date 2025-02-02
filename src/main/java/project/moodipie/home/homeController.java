package project.moodipie.home;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.dto.SessionUser;
import project.moodipie.handler.exeption.RestfullException;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.service.PlaylistService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class homeController {

    private final PlaylistService playlistService;
    private final HttpSession session;

    @Operation(summary = "홈에서 플레이리스트 조회", description = "홈 화면에서 플레이리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @GetMapping("/home")
    public ResponseEntity<List<PlaylistResponse>> findAllPlaylist() {
        SessionUser currentUser = getSessionUser();
        List<PlaylistResponse> allPlaylist = playlistService.findAllPlaylist(currentUser.getId());
        return ResponseEntity.ok(allPlaylist);
    }

    @Operation(summary = "플레이리스트 삭제", description = "홈 화면에서 플레이리스트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @DeleteMapping("/home")
    public void deletePlaylist(@RequestBody List<Long> ids) {
        playlistService.deletePlaylist(ids);
    }

    private SessionUser getSessionUser() {
        SessionUser currentUser = (SessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new RestfullException("User is not logged in.", HttpStatus.BAD_REQUEST);
        }
        return currentUser;
    }
}
