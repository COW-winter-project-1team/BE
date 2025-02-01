package project.moodipie.music.playlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.request.PlaylistTrackRequest;
import project.moodipie.music.playlist.controller.dto.request.UpdatePlaylistRequest;
import project.moodipie.music.playlist.service.PlaylistService;
import project.moodipie.music.track.controller.dto.response.TrackResponse;

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

    @GetMapping("/playlists/{id}")
    public List<TrackResponse> playlistResponse(@PathVariable("id") Long id) {
        return playlistService.findPlaylistById(id);
    }

    @PutMapping("/playlists/{id}")
    public void updatePlaylist(@PathVariable("id") Long id,
                               @RequestBody UpdatePlaylistRequest updatePlaylistRequest){
        playlistService.updatePlaylist(id, updatePlaylistRequest);
    }

    @DeleteMapping("/playlists/{playlistId}/tracks")
    public void deletePlaylistTrack(@PathVariable("playlistId") Long playlistId,
                                    @RequestBody PlaylistTrackRequest request) {
        playlistService.deletePlaylistTrack(playlistId, request.getTrackId());
    }



}
