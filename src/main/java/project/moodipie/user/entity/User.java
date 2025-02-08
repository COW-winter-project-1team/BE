package project.moodipie.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.playlist.entity.PlaylistTrack;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private boolean firstLogin;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PlaylistTrack> playlistTracks = new ArrayList<>();

    @Builder
    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.firstLogin = true;
    }
    public void updateName(String name) {
        this.name = name;
    }
}