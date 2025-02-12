package project.moodipie.config.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.moodipie.custom.InvalidFieldFormatException;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class LoginExceptionHandler {

    @ExceptionHandler(InvalidFieldFormatException.class)
    protected ResponseEntity<ApiRes<?>> handleInvalidFieldFormatException(InvalidFieldFormatException exception) {
        log.error("handleInvalidFieldFormatException", exception);
        String fieldName = exception.getFieldName();
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_PASSWORD_FORMAT, FieldErrors.of(fieldName, String.valueOf(exception.getCause()), exception.getMessage()));
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiRes<?>> handleNoSuchElementException(NoSuchElementException exception) {
        log.error("handleNoSuchElementException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_EMAIL_FORMAT);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

}
