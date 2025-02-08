package project.moodipie.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import project.moodipie.user.entity.User;

@Getter
public class SessionUser {
    @Schema(description = "유저의 아이디", example = "1")
    private Long id;
    @Schema(description = "이름", example = "홍길동")
    private String username;
    @Schema(description = "이메일", example = "moodipie@gmail.com")
    private String email;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.email = user.getEmail();
    }
}
