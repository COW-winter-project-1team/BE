package project.moodipie.music.track.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moodipie.music.playlist.entity.Playlist;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track {
    @Id
    @Column(name = "track_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    List<Album> albums = new ArrayList<>();

    @Builder
    public Track(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
