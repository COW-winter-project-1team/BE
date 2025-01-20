package project.moodipie.dto.response;
import lombok.Getter;
import project.moodipie.music.playlist.entity.Playlist;

import java.util.List;

@Getter
public class HomeResponse {
    private Long userID;
    private int playlistcount;
    private List<Playlist> playlists;

    public HomeResponse(Long userId, int playlistCount, List<Playlist> playlists){
        this.userID = userId;
        this.playlistcount = playlistCount;
        this.playlists = playlists;
    }
}
