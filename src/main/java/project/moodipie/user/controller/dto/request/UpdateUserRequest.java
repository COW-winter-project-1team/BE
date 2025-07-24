package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Schema(description = "수정된 이름", example = "둘리(공백X)")
    @NotBlank
    public String username;
    @Schema(description = "프로필 사진", example = "asdasdajsdpo.asd")
    @Nullable
    public String profileImageUrl;
}
