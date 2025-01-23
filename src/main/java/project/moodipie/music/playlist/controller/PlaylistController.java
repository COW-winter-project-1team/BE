package project.moodipie.music.playlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.service.PlaylistService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlists")
public class PlaylistController {

private final PlaylistService playlistService;

    @PostMapping("save")
    public void savePlaylist(@RequestBody CreatePlaylistRequest request) {
        playlistService.savePlaylist(request);
    }
}
