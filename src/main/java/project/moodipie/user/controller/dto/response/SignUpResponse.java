package project.moodipie.user.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpResponse {
    @Schema(description = "메세지", example = "회원가입이 완료되었습니다.")
    private String message;
    @Builder
    public SignUpResponse(String message) {
        this.message = message;
    }
}
