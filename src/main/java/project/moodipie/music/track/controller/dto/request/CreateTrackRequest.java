package project.moodipie.music.track.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import project.moodipie.music.track.entity.Track;

@Getter
public class CreateTrackRequest {
    @Schema(description = "트랙 ID", example = "sdakfjiosdwn1dnfsd")
    private String trackId;
    @Schema(description = "트랙 제목", example = "바다의 왕자")
    private String trackName;
    @Schema(description = "가수이름", example = "박명수")
    private String artistName;
    @Schema(description = "이미지 URL", example = "http://dfdfjiweoif.com")
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
