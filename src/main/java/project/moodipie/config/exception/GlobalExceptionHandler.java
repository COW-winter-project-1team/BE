package project.moodipie.config.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;
import project.moodipie.user.handler.exception.RestfullException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiRes<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_VALUE, FieldErrors.of(bindingResult));
        return ResponseEntity.badRequest().body(error);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiRes<?>> handlerIllegalArgumentException(IllegalArgumentException exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_VALUE);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ApiRes<?>> handlerNullPointerException(NullPointerException exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.NULL_VALUE);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ApiRes<?>> handleJsonProcessingException(Exception exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_FORMAT);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<ApiRes<?>> handleFeignException(FeignException exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.SPOTIFY_ERROR);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ExceptionHandler(RestfullException.class)
    public ResponseEntity<Map<String, Object>> handleRestfullException(RestfullException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", ex.getStatus().value());
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

}
