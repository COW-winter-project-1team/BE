package project.moodipie.music.track.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import project.moodipie.music.track.entity.Track;

@Getter
public class CreateTrackRequest {
    @Schema(description = "트랙 ID", example = "sdakfjiosdwn1dnfsd")
    @NotNull
    private String trackId;
    @Schema(description = "트랙 제목", example = "바다의 왕자")
    @NotNull
    private String trackName;
    @Schema(description = "가수이름", example = "박명수")
    @NotNull
    private String artistName;
    @Schema(description = "이미지 URL", example = "http://dfdfjiweoif.com")
    @NotNull
    private String imageUrl;


    public Track toEntity() {
        return Track.builder()
                .id(trackId)
                .trackName(trackName)
                .artistName(artistName)
                .imageUrl(imageUrl)
                .build();
    }
}
