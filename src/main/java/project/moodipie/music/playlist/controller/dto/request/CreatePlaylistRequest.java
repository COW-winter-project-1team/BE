package project.moodipie.music.playlist.controller.dto.request;

import lombok.Getter;
import project.moodipie.entity.Emotion;
import project.moodipie.music.playlist.entity.Playlist;

import java.util.Date;
import java.util.List;

@Getter
public class CreatePlaylistRequest {
    private String title;
    private String playlistImage;
    private Date timestamp;
    private Emotion emotion;
    protected List<String> trackIds;

    public Playlist toEntity() {
        return Playlist.builder()
                .title(title)
                .playlistImage(playlistImage)
                .timestamp(timestamp)
                .emotion(emotion)
                .build();
    }
}
