package project.moodipie.music.track.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.music.track.entity.Track;

@Getter
public class TrackResponse {
    @Schema(description = "트랙 ID", example = "sdakfjiosdwn1dnfsd")
    private String trackId;
    @Schema(description = "트랙 제목", example = "바다의 왕자")
    private String trackName;
    @Schema(description = "가수이름", example = "박명수")
    private String artistName;
    @Schema(description = "이미지 URL", example = "http://dfdfjiweoif.com")
    private String imageUrl;

    @Builder
    public TrackResponse(String trackId, String trackName, String artistName, String imageUrl) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }

    public static TrackResponse from(Track track) {
        return TrackResponse.builder()
                .trackId(track.getId())
                .trackName(track.getTrackName())
                .artistName(track.getArtistName())
                .imageUrl(track.getImageUrl())
                .build();
    }
}
