package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Schema(description = "수정된 이름", example = "둘리")
    public String name;
    @Schema(description = "프로필 사진", example = "12388")
    public Integer profilePicture;
}
