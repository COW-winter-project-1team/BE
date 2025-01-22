package project.moodipie.music.playlist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moodipie.entity.Emotion;
import project.moodipie.entity.User;
import project.moodipie.music.PlaylistTrack;
import project.moodipie.music.track.entity.Track;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long id;

    //TODO: User 객체 확인 후.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    private String title;
    private String playlistImage;
    private Date timestamp;
    private Emotion emotion;


    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    List<PlaylistTrack> playlistTracks = new ArrayList<>();


    @Builder
    public Playlist(final String title, final String playlistImage, final Date timestamp, final Emotion emotion) {
        this.title = title;
        this.playlistImage = playlistImage;
        this.timestamp = timestamp;
        this.emotion = emotion;
    }

}