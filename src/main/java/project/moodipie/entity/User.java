package project.moodipie.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.moodipie.music.playlist.entity.Playlist;

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

//    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
//    private List<Playlist> playlists = new ArrayList<>();

    @Builder
    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public void updateName(String name) {
        this.name = name;
    }
    public void addPlaylist(Playlist playlist) {

    }
    public void removePlaylist(Playlist playlist) {

    }
}