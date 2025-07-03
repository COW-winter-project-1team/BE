package project.moodipie.config.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    protected ResponseEntity<ApiRes<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_VALUE, FieldErrors.of(bindingResult));
        return ResponseEntity.badRequest().body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    protected ResponseEntity<ApiRes<?>> handlerIllegalArgumentException(IllegalArgumentException exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_VALUE);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ApiRes<?>> handlerNullPointerException(NullPointerException exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.NULL_VALUE);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ApiRes<?>> handleJsonProcessingException(Exception exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_FORMAT);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    //경로변수 누락시 예외
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiRes<?>> handlerAccessDeniedException(AccessDeniedException exception) {
        ApiRes<Object> error = ApiRes.error(ErrorCode.MISSING_PATH);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

}
