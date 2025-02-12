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
    private final String reason;

    protected static List<FieldErrors> of(final String field, final String value, final String reason) {
        List<FieldErrors> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldErrors(field, value, reason));
        return fieldErrors;
    }

    public static List<FieldErrors> of(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(error -> new FieldErrors(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @Builder
    FieldErrors(String field, String value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }
}

