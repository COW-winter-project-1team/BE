package project.moodipie.music.playlist.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.Emotion;
import project.moodipie.music.playlist.entity.Playlist;

import java.time.LocalDate;

@Getter
public class PlaylistResponse {
    @Schema(description = "플레이 리스트 id", example = "1")
    private Long playlistNumber;
    @Schema(description = "제목", example = "http://dfdfjiweoif.com")
    private String title;
    @Schema(description = "플레이리스트 이미지 URL", example = "http://dfdfjiweoif.com")
    private String playlistImage;
    @Schema(description = "플레이리스트 생성 시간", example = "2024-12-12")
    private LocalDate timestamp;
    @Schema(description = "감정", example = "SAD")
    private Emotion emotion;

    @Builder
    public PlaylistResponse(Long playlistNumber, String title, String playlistImage, LocalDate timestamp, Emotion emotion) {
        this.playlistNumber = playlistNumber;
        this.title = title;
        this.playlistImage = playlistImage;
        this.timestamp = timestamp;
        this.emotion = emotion;
    }

    public static PlaylistResponse from(Playlist playlist) {
        return PlaylistResponse.builder()
                .playlistNumber(playlist.getPlaylistNumber())
                .title(playlist.getTitle())
                .playlistImage(playlist.getPlaylistImage())
                .timestamp(playlist.getTimestamp())
                .emotion(playlist.getEmotion())
                .build();
    }
}
