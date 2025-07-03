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

import java.util.List;
import java.util.NoSuchElementException;

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
        List<FieldErrors> errors = FieldErrors.of("argument", "", exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_VALUE, errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ApiRes<?>> handlerNullPointerException(NullPointerException exception) {
        List<FieldErrors> errors = FieldErrors.of("value", "", exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.NULL_VALUE, errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ApiRes<?>> handleJsonProcessingException(JsonProcessingException exception) {
        List<FieldErrors> errors = FieldErrors.of("json", "", exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_FORMAT, errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiRes<?>> handlerAccessDeniedException(AccessDeniedException exception) {
        List<FieldErrors> errors = FieldErrors.of("access", "", exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.MISSING_PATH, errors);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "CONFLICT", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ApiRes<?>> handleIllegalStateException(IllegalStateException exception) {
        List<FieldErrors> errors = FieldErrors.of("state", "", exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.CONFLICT, errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiRes<?>> handleNoSuchElementException(NoSuchElementException exception) {
        List<FieldErrors> errors = FieldErrors.of("element", "", exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.RESOURCE_NOT_FOUND, errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
