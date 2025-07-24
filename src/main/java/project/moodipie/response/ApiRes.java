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
    @Schema(description = "응답 상태", example = "success 또는 fail")
    @JsonView(CustomJsonView.Common.class)
    private final String status;

    @Schema(description = "HTTP 응답코드", example = "3자리 정수형 ex.200")
    @JsonView(CustomJsonView.Common.class)
    private final int httpStatus;

    @Schema(description = "API 요청에 대한 전반적인 요약 메세지", example = "ex.조회에 성공했습니다.")
    @JsonView(CustomJsonView.Common.class)
    private final String message;

    @Schema(description = "응답 데이터")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView(CustomJsonView.Hidden.class)
    private final T data;

    @Schema(description = "오류 구분 코드", example = "오류시 출력")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView(CustomJsonView.Hidden.class)
    private final String divisionCode;

    @Schema(description = "상세 필드 에러",  example = "오류시 출력")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<FieldErrors> errors;

    @Builder
    public ApiRes(String status, int httpStatus, String message, T data, String divisionCode, List<FieldErrors> errors) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
        this.divisionCode = divisionCode;
        this.errors = errors;
    }

    // 성공 응답 팩토리 메서드들 (메시지 커스터마이징 가능한 버전 추가)
    public static <T> ApiRes<T> created(final T data, String message) {
        return ApiRes.<T>builder()
                .status("success")
                .httpStatus(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(message)
                .data(data)
                .build();
    }
    public static <T> ApiRes<T> created(final T data) {
        return created(data, SuccessCode.INSERT_SUCCESS.getMessage());
    }


    public static <T> ApiRes<T> ok(final T data) {
        return ApiRes.<T>builder()
                .status("success")
                .httpStatus(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiRes<T> ok(final T data, String message) {
        return ApiRes.<T>builder()
                .status("success")
                .httpStatus(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiRes<T> update(final T data) {
        return ApiRes.<T>builder()
                .status("success")
                .httpStatus(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage()) //
                .data(data)
                .build();
    }

    public static <T> ApiRes<T> update(final T data, String message) {
        return ApiRes.<T>builder()
                .status("success")
                .httpStatus(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiRes<T> delete(final T data, String message) {
        return ApiRes.<T>builder()
                .status("success")
                .httpStatus(SuccessCode.DELETE_SUCCESS.getStatus())
                .message(message)
                .data(data)
                .build();
    }
    public static <T> ApiRes<T> delete(final T data) {
        return delete(data, SuccessCode.DELETE_SUCCESS.getMessage());
    }

    // 실패 응답 팩토리 메서드들은 기존대로 유지 (FieldErrors에 code와 message가 포함되도록 했으므로)
    public static <T> ApiRes<T> error(ErrorCode code, List<FieldErrors> errors) {
        return ApiRes.<T>builder()
                .status("fail")
                .httpStatus(code.getStatus())
                .message(code.getMessage())
                .divisionCode(code.getDivisionCode())
                .errors(errors)
                .build();
    }

    public static <T> ApiRes<T> error(ErrorCode code) {
        return ApiRes.<T>builder()
                .status("fail")
                .httpStatus(code.getStatus())
                .message(code.getMessage())
                .divisionCode(code.getDivisionCode())
                .build();
    }

    public static <T> ApiRes<T> error(ErrorCode code, String customMessage) {
        return ApiRes.<T>builder()
                .status("fail")
                .httpStatus(code.getStatus())
                .message(customMessage)
                .divisionCode(code.getDivisionCode())
                .build();
    }

    public static <T> ApiRes<T> error(ErrorCode code, String customMessage, List<FieldErrors> errors) {
        return ApiRes.<T>builder()
                .status("fail")
                .httpStatus(code.getStatus())
                .message(customMessage)
                .divisionCode(code.getDivisionCode())
                .errors(errors)
                .build();
    }
}