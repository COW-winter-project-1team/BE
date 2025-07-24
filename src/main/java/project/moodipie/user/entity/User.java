package project.moodipie.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.playlist.entity.PlaylistTrack;
import project.moodipie.user.controller.dto.request.UpdateUserRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private boolean firstLogin;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "profile_image_url", nullable = true)
    @Nullable
    private String profileImageUrl;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PlaylistTrack> playlistTracks = new ArrayList<>();

    @Builder
    private User(String name, String email, String password,String profileImageUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.firstLogin = true;
        this.createdAt = LocalDateTime.now();
        this.profileImageUrl = profileImageUrl;
    }
    public void updateName(UpdateUserRequest updateUserRequest) {
        this.name = updateUserRequest.getUsername();
        this.profileImageUrl = updateUserRequest.getProfileImageUrl();
    }
}