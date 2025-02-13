package project.moodipie.music.playlist.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import project.moodipie.Emotion;

@Getter
@Setter
public class UpdatePlaylistRequest {
    @Schema(description = "플레이리스트 제목", example = "우울할때 듣는 음악")
    @NotNull
    private String title;
    @Schema(description = "플레이리스트 이미지 URL", example = "http://dfdfjiweoif.com")
    @NotNull
    private String playlistImage;
    @Schema(description = "감정", example = "SAD")
    @NotNull
    private Emotion emotion;
}
