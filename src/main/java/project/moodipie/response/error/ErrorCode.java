package project.moodipie.response.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    VALID_EMPTY_EXCEPTION(400, "G001", "모든 값이 존재해야 합니다."),
    TOKEN_EXPIRED(401, "T001", "토큰이 만료되었습니다."),
    TOKEN_INVALID(401, "T002", "토큰이 유효하지 않습니다."),
    TOKEN_FAIL(401, "T003", "올바르지 않은 토큰입니다.");

    private final int status;
    private final String divisionCode;
    private final String message;

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
