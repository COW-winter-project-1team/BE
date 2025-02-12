package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import project.moodipie.user.entity.User;

@Getter
public class CreateUserRequest {
    @Schema(description = "이름", example = "홍길동")
    @NotBlank
    private String username;
    @Schema(description = "이메일", example = "moodipie@gmail.com")
    @NotBlank
    private String email;
    @Schema(description = "password", example = "1234")
    @NotBlank
    private String password;

    public User toEntity(){
        return  User.builder()
                .name(username)
                .email(email)
                .password(password)
                .build();
    }
}
