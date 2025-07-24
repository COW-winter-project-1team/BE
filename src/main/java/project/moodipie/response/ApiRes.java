package project.moodipie.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;
import project.moodipie.response.success.SuccessCode;
import project.moodipie.swagger.CustomJsonView;

import java.util.List;

@Getter
@Schema(title = "API 응답")
public final class ApiRes<T> {
    @Schema(description = "응답코드", example = "3자리 정수형 ex.200")
    @JsonView(CustomJsonView.Common.class)
    private final int httpStatus;
    @Schema(description = "응답 데이터")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView(CustomJsonView.Hidden.class)
    private final T data;
    @Schema(description = "응답 메세지", example = "ex.조회에 성공했습니다.")
    @JsonView(CustomJsonView.Common.class)
    private final String message;
    @Schema(description = "오류 구분 코드", example = "오류시 출력")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView(CustomJsonView.Hidden.class)
    private final String divisionCode;
    @Schema(description = "상세 필드 에러",  example = "오류시 출력")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<FieldErrors> errors;

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

    public static <T> ApiRes<T> ok(final T data) {
        return ApiRes.<T>builder()
                .httpStatus(SuccessCode.SELECT_SUCCESS.getStatus())
                .data(data)
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
    }

    public static <T> ApiRes<T> update(final T data) {
        return ApiRes.<T>builder()
                .httpStatus(SuccessCode.UPDATE_SUCCESS.getStatus())
                .data(data)
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();
    }

    public static <T> ApiRes<T> delete(final T data) {
        return ApiRes.<T>builder()
                .httpStatus(SuccessCode.DELETE_SUCCESS.getStatus())
                .data(data)
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
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

    public static <T> ApiRes<T> error(ErrorCode code) {
        return ApiRes.<T>builder()
                .httpStatus(code.getStatus())
                .divisionCode(code.getDivisionCode())
                .message(code.getMessage())
                .build();
    }

}
