package project.moodipie.response.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FieldErrors {
    private final String field;
    private final String value;
    private final String code; // 에러 종류를 나타내는 코드 추가 (예: EMAIL_ALREADY_EXISTS, INVALID_FORMAT)
    private final String message; // 상세 메시지로

    public static List<FieldErrors> of(final String field, final String value, final String code, final String message) {
        List<FieldErrors> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldErrors(field, value, code, message));
        return fieldErrors;
    }

    public static List<FieldErrors> of(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(error -> new FieldErrors(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getCode() != null ? error.getCode() : "VALIDATION_ERROR",
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @Builder
    FieldErrors(String field, String value, String code, String message) {
        this.field = field;
        this.value = value;
        this.code = code;
        this.message = message;
    }
}