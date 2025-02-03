package project.moodipie.user.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpResponse {
    private String message;
    @Builder
    public SignUpResponse(String message) {
        this.message = message;
    }
}
