package project.moodipie.playlist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moodipie.track.entity.Track;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    //TODO: User 객체 확인 후.
//    @ManyToOne
//    @JoinColumn(name = "user_id",insertable = false, updatable = false)
//    private User user;

    private String title;
    private String playlistImage;
    private Date timestamp;
    private Emotion emotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    @Builder
    public Playlist(final String title, final String playlistImage, final Date timestamp, final Emotion emotion, final Track track){
        this.title = title;
        this.playlistImage = playlistImage;
        this.timestamp = timestamp;
        this.emotion = emotion;
        this.track = track;
    }

}
