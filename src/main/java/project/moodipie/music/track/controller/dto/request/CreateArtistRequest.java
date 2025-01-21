package project.moodipie.music.track.controller.dto.request;

import lombok.Getter;
import project.moodipie.music.track.entity.Album;
import project.moodipie.music.track.entity.Artist;

@Getter
public class CreateArtistRequest {
    private String id;
    private String name;
    private Album album;

    public Artist toEntity() {
        return Artist.builder()
                .id(id)
                .name(name)
                .album(album)
                .build();
    }
}
