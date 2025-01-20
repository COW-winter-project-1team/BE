package project.moodipie.dto;

import lombok.Getter;
import project.moodipie.entity.User;

@Getter
public class SessionUser {
    private String username;
    private String email;
    private Integer picture;

    public SessionUser(User user) {
        this.username = user.getName();
        this.email = user.getEmail();
        this.picture = user.getProfileImage();
    }
}
