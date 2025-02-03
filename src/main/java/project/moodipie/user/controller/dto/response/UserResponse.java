package project.moodipie.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "이름", example = "홍길동")
    private String username;
    @Schema(description = "프로필 사진", example = "13855")
    private Integer ProfilePicture;
}
