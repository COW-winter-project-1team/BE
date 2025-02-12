package project.moodipie.config.exception_handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;

@RestControllerAdvice(basePackages = "config")
@Slf4j
public class JwtExceptionHandler {
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
