package project.moodipie.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    VALID_EMPTY_EXCEPTION(404, "G001", "모든 값이 존재해야 합니다.");

    private final int status;
    private final String divisionCode;
    private final String message;

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
