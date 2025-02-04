package project.moodipie.music.playlist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moodipie.music.track.entity.Track;
import project.moodipie.user.entity.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "playlist_track")
public class PlaylistTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long playlistTrackId;

    @Builder
    public PlaylistTrack(Playlist playlist, Track track, User user , Long playlistTrackId) {
        this.playlist = playlist;
        this.track = track;
        this.user = user;
        this.playlistTrackId = playlistTrackId;
    }

}
