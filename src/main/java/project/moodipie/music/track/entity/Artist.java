package project.moodipie.music.track.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artist {
    @Id
    @Column(name = "artist_id")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "tarck_id")
    private Track track;

    @Builder
    public Artist(String id, String name, Track track) {
        this.id = id;
        this.name = name;
        this.track = track;
    }

}
