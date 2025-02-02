package project.moodipie.spotify.configuration.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SpotifyTrackRequest {
    @Schema(description = "트랙 제목", example = "바다의 왕자")
    private String trackName;

    @Schema(description = "가수이름", example = "박명수")
    private String artistName;
}
