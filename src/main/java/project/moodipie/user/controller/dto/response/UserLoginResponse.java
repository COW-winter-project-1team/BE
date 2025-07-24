package project.moodipie.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginResponse {
    @Schema(description = "메세지", example = "회원가입이 완료되었습니다.")
    private String message;
    @Schema(description = "토큰", example = "asdjasdiaojsdasdjaisd2132i438rdhi2d393")
    private String token;
    @Builder
    public UserLoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
