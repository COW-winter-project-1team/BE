package project.moodipie.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiRes<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("handleMethodArgumentNotValidException", exception);
        BindingResult bindingResult = exception.getBindingResult();
        ApiRes<Object> error = ApiRes.error(ErrorCode.VALID_EMPTY_EXCEPTION, FieldErrors.of(bindingResult));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ApiRes<?>> handleExpiredJwtException(ExpiredJwtException exception) {
        log.error("handleExpiredJwtException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_EXPIRED);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiRes<?>> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_INVALID);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ApiRes<?>> handleMalformedJwtException(MalformedJwtException exception) {
        log.error("handleMalformedJwtException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_FAIL);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

}
