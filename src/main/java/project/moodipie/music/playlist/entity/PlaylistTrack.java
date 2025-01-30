package project.moodipie.music.playlist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moodipie.music.track.entity.Track;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "playlist_track")
@IdClass(PlaylistTrackId.class)
public class PlaylistTrack {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id")
    private Track track;

    private int playlistTrackId;

    @Builder
    public PlaylistTrack(Playlist playlist, Track track, int playlistTrackId) {
        this.playlist = playlist;
        this.track = track;
        this.playlistTrackId = playlistTrackId;
    }

}
