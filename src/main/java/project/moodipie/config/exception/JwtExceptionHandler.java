package project.moodipie.config.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;

@RestControllerAdvice
@Slf4j
public class JwtExceptionHandler {
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ApiRes<?>> handleExpiredJwtException(ExpiredJwtException exception) {
        log.error("handleExpiredJwtException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_EXPIRED);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ApiRes<?>> handleMalformedJwtException(MalformedJwtException exception) {
        log.error("handleMalformedJwtException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_FORMAT_ERROR);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ApiRes<?>> handleSignatureException(SignatureException exception) {
        log.error("handleSignatureException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_SIGNATURE_INVALID);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }
    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<ApiRes<?>> handleJwtException(JwtException exception) {
        log.error("JwtException", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_ERROR);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

}
