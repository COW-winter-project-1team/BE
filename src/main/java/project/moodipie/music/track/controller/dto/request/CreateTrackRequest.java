package project.moodipie.music.track.controller.dto.request;

import lombok.Getter;
import project.moodipie.music.track.entity.Track;

@Getter
public class CreateTrackRequest {
    private String trackId;
    private String trackName;
    private String artistName;
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
