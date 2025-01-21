package project.moodipie.music.track.controller.dto.request;

import lombok.Getter;
import project.moodipie.music.track.entity.Album;
import project.moodipie.music.track.entity.Track;

@Getter
public class CreateAlbumRequest {
    private String id;
    private String name;
    private Track track;

    public Album toEntity() {
        return Album.builder()
                .id(id)
                .name(name)
                .track(track)
                .build();
    }
}
