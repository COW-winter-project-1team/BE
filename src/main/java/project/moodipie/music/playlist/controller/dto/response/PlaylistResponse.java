package project.moodipie.music.playlist.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.entity.Emotion;
import project.moodipie.music.playlist.entity.Playlist;

import java.util.Date;
import java.util.List;

@Getter
public class PlaylistResponse {
    @Schema(description = "제목", example = "http://dfdfjiweoif.com")
    private String title;
    @Schema(description = "플레이리스트 이미지 URL", example = "http://dfdfjiweoif.com")
    private String playlistImage;
    @Schema(description = "플레이리스트 생성 시간", example = "2024-12-12T12:12:12")
    private Date timestamp;
    @Schema(description = "감정", example = "SAD")
    private Emotion emotion;

    @Builder
    public PlaylistResponse(String title, String playlistImage, Date timestamp, Emotion emotion) {
        this.title = title;
        this.playlistImage = playlistImage;
        this.timestamp = timestamp;
        this.emotion = emotion;
    }

    public static PlaylistResponse from(Playlist playlist) {
        return PlaylistResponse.builder()
                .title(playlist.getTitle())
                .playlistImage(playlist.getPlaylistImage())
                .timestamp(playlist.getTimestamp())
                .emotion(playlist.getEmotion())
                .build();
    }
}
