package project.moodipie.music.track.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album {
    @Id
    @Column(name = "album_id")
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    List<Artist> artists = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    List<Image> images = new ArrayList<>();

    @Builder
    public Album(String id, String name, Track track) {
        this.id = id;
        this.name = name;
        this.track = track;
    }
}