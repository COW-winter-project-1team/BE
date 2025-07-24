package project.moodipie.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.user.entity.User;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    @Schema(description = "이름", example = "홍길동")
    private String username;

    @Schema(description = "이메일", example = "moodipie@gmail.com")
    private String email;

    @Schema(description = "계정 생성일", example = "2025-07-23T10:00:00")
    private final LocalDateTime createdAt;

    @Schema(description = "프로필 사진", example = "asdasdajsdpo.asd")
    @Nullable
    public String profileImageUrl;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .username(user.getName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .createdAt(user.getCreatedAt())
                .build();

    }

}
