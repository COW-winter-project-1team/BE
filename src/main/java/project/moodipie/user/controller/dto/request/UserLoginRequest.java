package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLoginRequest {
    @Schema(description = "이메일", example = "moodipie@gmail.com")
    @NotNull
    private String email;
    @Schema(description = "비밀번호", example = "1234")
    @NotNull
    private String password;
}
