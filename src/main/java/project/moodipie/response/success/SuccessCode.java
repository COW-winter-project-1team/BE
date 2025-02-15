package project.moodipie.response.success;

import lombok.Getter;

@Getter
public enum SuccessCode {
    /**
     * ******************************* Success CodeList ***************************************
     */
    // 조회 성공 코드 (HTTP Response: 200 OK)
    SELECT_SUCCESS(200, "S001", "조회 성공"),
    // 삭제 성공 코드 (HTTP Response: 200 OK)
    DELETE_SUCCESS(200, "S002", "삭제 성공"),
    // 생성 성공 코드 (HTTP Response: 201 created)
    INSERT_SUCCESS(201, "S003", "생성 성공"),
    // 수정 성공 코드 (HTTP Response: 204 created)
    UPDATE_SUCCESS(200, "S004", "수정 성공");

    private final int status;
    private final String divisionCode;
    private final String message;

    SuccessCode(final int status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
