package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import project.moodipie.user.entity.User;

@Getter
@Setter
public class CreateUserRequest {
    @Schema(description = "이름", example = "홍길동")
    private String username;
    @Schema(description = "이메일", example = "moodipie@gmail.com")
    private String email;
    @Schema(description = "password", example = "1234")
    private String password;

    public User toEntity(){
        return  User.builder()
                .name(username)
                .email(email)
                .password(password)
                .build();
    }
}
