package project.moodipie.response;


import lombok.Builder;
import lombok.Getter;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;
import project.moodipie.response.success.SuccessCode;

import java.util.List;

@Getter
public final class ApiRes<T> {
    private final int httpStatus;
    private final T data;
    private final String message;
    private final String divisionCode; // 오류 구분 코드
    private final List<FieldErrors> errors; // 상세 에러 메시지

    @Builder
    public ApiRes(int httpStatus, T data, String message, String divisionCode, String reason, List<FieldErrors> errors) {
        this.httpStatus = httpStatus;
        this.data = data;
        this.message = message;
        this.divisionCode = divisionCode;
        this.errors = errors;
    }


    public static <T> ApiRes<T> created(final T data) {
        return ApiRes.<T>builder()
                .httpStatus(SuccessCode.INSERT_SUCCESS.getStatus())
                .data(data)
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
    }

    // 실패 응답 (오류 코드 + `FieldErrors`)
    public static <T> ApiRes<T> error(ErrorCode code, List<FieldErrors> errors) {
        return ApiRes.<T>builder()
                .httpStatus(code.getStatus())
                .divisionCode(code.getDivisionCode())
                .message(code.getMessage())
                .errors(errors)
                .build();
    }

}
