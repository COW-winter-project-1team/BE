package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import project.moodipie.user.entity.User;

@Getter
public class CreateUserRequest {
    @Schema(description = "이름", example = "홍길동")
    @NotBlank
    private String username;
    @Schema(description = "이메일", example = "moodipie@gmail.com")
    @Email
    @NotBlank
    private String email;
    @Schema(description = "password", example = "1234")
    @NotBlank
    @Size(max = 8, message = "8자 이상의 영문, 특수문자, 숫자 조합")
    private String password;

    public User toEntity(){
        return  User.builder()
                .name(username)
                .email(email)
                .password(password)
                .build();
    }
}
