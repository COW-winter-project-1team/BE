package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginRequest {
    @Schema(description = "이메일", example = "moodipie@gmail.com")
    @Email
    @NotBlank
    private String email;
    @Schema(description = "비밀번호", example = "1234")
    @NotBlank
    private String password;
}
