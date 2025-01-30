package project.moodipie.music.playlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.service.PlaylistService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name="플레이리스트", description ="플레이리스트 CRUD")
public class PlaylistController {

private final PlaylistService playlistService;

    @Operation(summary = "플레이리스트 생성", description = "가져온 음악을 새로운 플레이리스트에 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
            @ApiResponse(responseCode = "400", description = "저장 실패")
    })
    @PostMapping("/playlists/save")
    public void savePlaylist(@RequestBody CreatePlaylistRequest request) {
        playlistService.savePlaylist(request);
    }

    @GetMapping("/user/{userId}/playlists")
    public List<PlaylistResponse> findAllPlaylist(@PathVariable("userId") Long userId) {
        return playlistService.findAllPlaylist(userId);
    }


}
