package project.moodipie.controller.dto;

import lombok.Getter;
import project.moodipie.entity.Emotion;
import project.moodipie.playlist.entity.Playlist;

@Getter
public class CreatePlaylistRequest {

    private String playlistTitle;
    private String musiclist;
    private Emotion emotion;
    private String PlaylistURL;
    private int playlistpicture;

    public Playlist toEntity(){
        return  Playlist.builder()
                .title(playlistTitle)
                .playlistImage(musiclist)
                .emotion(emotion)
                .build();
    }
}
