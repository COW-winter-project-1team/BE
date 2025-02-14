package project.moodipie.response.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 클라이언트 오류 (400)
    INVALID_VALUE(400, "G001", "유효하지 않은 값이 존재합니다."),
    INVALID_EMAIL(400, "L001", "해당하는 이메일이 없습니다."),
    INVALID_PASSWORD(400, "L002", "잘못된 비밀번호입니다."),
    NULL_VALUE(400, "G003", "NULL값이 존재합니다."),
    INVALID_FORMAT(400, "G002", "잘못된 형식입니다."),


    // 토큰 오류 (401)
    JWT_ERROR(401, "S001","jwt 에러입니다."),
    TOKEN_EXPIRED(401, "T001", "토큰이 만료되었습니다."),
    TOKEN_INVALID(401, "T002", "토큰이 유효하지 않습니다."),
    TOKEN_FORMAT_ERROR(401, "T003", "잘못된 형식의 토큰입니다."),
    TOKEN_SIGNATURE_INVALID(401, "T004", "토큰 무결성이 유효하지 않습니다."),
    TOKEN_ERROR(401, "T005", "토큰 관련 에러입니다."),
    NULL_TOKEN(401,"T000","토큰이 비었습니다."),

    INTERNAL_SERVER_ERROR(500, "G003", "서버 오류 입니다"),
    //스포티파이 에러 (아무 곡도 못찾았을 때)
    SPOTIFY_ERROR(404, "S001","스포티파이에서 곡을 찾지 못했습니다.");

    private final int status;
    private final String divisionCode;
    private final String message;

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
