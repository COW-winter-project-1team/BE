package project.moodipie.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.moodipie.user.entity.User;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserLoginResponse {

    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;

    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String refreshToken;

    @Schema(description = "사용자 정보")
    private UserInfoResponse userInfo;

    @Schema(description = "첫 로그인 여부", example = "true")
    private boolean isFirstLogin;

    public static UserLoginResponse from(User user, String accessToken, String refreshToken,boolean isFirstLogin) {
        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userInfo(UserInfoResponse.from(user))
                .isFirstLogin(isFirstLogin)
                .build();
    }
}