package project.moodipie.user.controller.dto;

import lombok.Getter;
import project.moodipie.user.entity.User;

@Getter
public class SessionUser {
    private Long id;
    private String username;
    private String email;
    private Integer picture;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.email = user.getEmail();
        this.picture = user.getProfileImage();
    }
}
