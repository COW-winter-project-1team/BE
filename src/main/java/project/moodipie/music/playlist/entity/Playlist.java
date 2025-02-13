package project.moodipie.music.playlist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moodipie.Emotion;
import project.moodipie.music.playlist.controller.dto.request.UpdatePlaylistRequest;
import project.moodipie.user.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long playlistNumber;
    private String title;
    private String playlistImage;
    private LocalDate timestamp;
    private Emotion emotion;


    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    List<PlaylistTrack> playlistTracks = new ArrayList<>();


    @Builder
    public Playlist(Long playlistNumber,final User user, final String title, final String playlistImage, final LocalDate timestamp, final Emotion emotion) {
        this.playlistNumber = playlistNumber;
        this.user = user;
        this.title = title;
        this.playlistImage = playlistImage;
        this.timestamp = timestamp;
        this.emotion = emotion;
    }

    public void update(UpdatePlaylistRequest updatePlaylistRequest) {
        this.title = updatePlaylistRequest.getTitle();
        this.playlistImage = updatePlaylistRequest.getPlaylistImage();
        this.emotion = updatePlaylistRequest.getEmotion();
    }
}