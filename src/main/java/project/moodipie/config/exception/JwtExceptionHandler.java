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
import project.moodipie.response.error.FieldErrors;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class JwtExceptionHandler {
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ApiRes<?>> handleExpiredJwtException(ExpiredJwtException exception) {
        log.error("유효 기간 만료", exception);
        List<FieldErrors> errors = FieldErrors.of("token", "", exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_EXPIRED, errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<ApiRes<?>> handleMalformedJwtException(MalformedJwtException exception) {
        log.error("JWT의 형식이 잘못되었거나 유효하지 않음", exception);
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_FORMAT_ERROR);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ApiRes<?>> handleSignatureException(SignatureException exception) {
        log.error("다른 secretKey로 만들어졌음", exception);
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
