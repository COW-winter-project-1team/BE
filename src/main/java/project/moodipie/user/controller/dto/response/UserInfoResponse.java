package project.moodipie.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.user.entity.User;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    @Schema(description = "이름", example = "홍길동")
    private String username;
    @Schema(description = "프로필 사진", example = "13855")
    private Integer profilePicture;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .username(user.getName())
                .profilePicture(user.getProfileImage())
                .build();
    }

}
