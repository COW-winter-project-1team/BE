package project.moodipie.music.playlist.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PlaylistTrackRequest {
    @Schema(description = "트랙 정보", example = "1")
    private Long trackId;
}
