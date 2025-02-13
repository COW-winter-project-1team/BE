package project.moodipie.music.playlist.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import project.moodipie.Emotion;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.user.entity.User;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreatePlaylistRequest {

    @Schema(description = "플레이리스트 제목", example = "우울할때 듣는 음악")
    @NotNull
    private String title;
    @Schema(description = "플레이리스트 이미지 URL", example = "http://dfdfjiweoif.com")
    @NotNull
    private String playlistImage;
    @Schema(description = "감정", example = "SAD")
    @NotNull
    private Emotion emotion;
    @Schema(description = "플레이리스트 트랙 ID들", example = "[\"7ouMYWpwJ422jRcDASZB7P\", \"4VqPOruhp5EdPBeR92t6lQ\", \"2takcwOaAZWiXQijPHIx7B\"]")
    @NotNull
    protected List<String> trackIds;

    public Playlist toEntity(User user, Long playlistNumber) {
        return Playlist.builder()
                .user(user)
                .playlistNumber(playlistNumber)
                .title(title)
                .playlistImage(playlistImage)
                .timestamp(LocalDate.now())
                .emotion(emotion)
                .build();
    }
}
