package project.moodipie.music.track.controller.dto.request;

import lombok.Getter;
import project.moodipie.music.track.entity.Album;
import project.moodipie.music.track.entity.Track;

@Getter
public class CreateTrackRequest {
    private String id;
    private String name;

    public Track toEntity() {
        return Track.builder()
                .id(id)
                .name(name)
                .build();
    }
}
