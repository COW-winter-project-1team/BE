package project.moodipie.music.track.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moodipie.music.PlaylistTrack;
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
    @Column(name = "track_name")
    private String trackName;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "image_Url")
    private String imageUrl;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    List<PlaylistTrack> playlistTracks = new ArrayList<>();

    @Builder
    public Track(String id, String trackName, String artistName, String imageUrl) {
        this.id = id;
        this.trackName = trackName;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }
}
