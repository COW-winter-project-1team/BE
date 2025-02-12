package project.moodipie.spotify.configuration.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpotifyTrackRequest {
    @Schema(description = "트랙 제목", example = "바다의 왕자")
    @NotNull
    private String trackName;

    @Schema(description = "가수이름", example = "박명수")
    @NotNull
    private String artistName;
}
