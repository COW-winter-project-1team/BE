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

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Artist> artist;
    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private List<Image> image;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Playlist> playlists = new ArrayList<>();


    @Builder
    public Track(String id, String name, List<Artist> artist, List<Image> image) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.image = image;
    }
}
