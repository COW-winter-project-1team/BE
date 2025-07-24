package project.moodipie.config.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;
import project.moodipie.user.exception.EmailAlreadyExistsException;
import project.moodipie.user.exception.PasswordMismatchException;
import project.moodipie.user.exception.UserNotFoundException;


import java.io.IOException;
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
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_VALUE, ErrorCode.INVALID_VALUE.name(), FieldErrors.of(bindingResult));
        return ResponseEntity.badRequest().body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    protected ResponseEntity<ApiRes<?>> handlerIllegalArgumentException(IllegalArgumentException exception) {
        List<FieldErrors> errors = FieldErrors.of("argument", "", ErrorCode.INVALID_VALUE.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_VALUE, ErrorCode.INVALID_VALUE.getMessage(), errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ApiRes<?>> handlerNullPointerException(NullPointerException exception) {
        List<FieldErrors> errors = FieldErrors.of("value", "", ErrorCode.NULL_VALUE.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.NULL_VALUE, ErrorCode.NULL_VALUE.getMessage(), errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ApiRes<?>> handleJsonProcessingException(JsonProcessingException exception) {
        List<FieldErrors> errors = FieldErrors.of("json", "", ErrorCode.INVALID_FORMAT.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_FORMAT, ErrorCode.INVALID_FORMAT.getMessage(), errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiRes<?>> handlerAccessDeniedException(AccessDeniedException exception) {
        List<FieldErrors> errors = FieldErrors.of("access", "", ErrorCode.UNAUTHORIZED.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.UNAUTHORIZED, ErrorCode.UNAUTHORIZED.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "CONFLICT", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ResponseEntity<ApiRes<?>> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {
        List<FieldErrors> errors = FieldErrors.of("email", exception.getEmail(), ErrorCode.EMAIL_CONFLICT.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.EMAIL_CONFLICT, ErrorCode.EMAIL_CONFLICT.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(PasswordMismatchException.class)
    protected ResponseEntity<ApiRes<?>> handlePasswordMismatchException(PasswordMismatchException exception) {
        List<FieldErrors> errors = FieldErrors.of("password", "", ErrorCode.PASSWORD_MISSMATCH.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.PASSWORD_MISSMATCH, ErrorCode.PASSWORD_MISSMATCH.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ApiRes<?>> handleUserNotFoundException(UserNotFoundException exception) {
        List<FieldErrors> errors = FieldErrors.of("user_identifier", "", ErrorCode.USER_NOT_FOUND.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiRes<?>> handleNoResourceFoundException(NoResourceFoundException exception) {
        List<FieldErrors> errors = FieldErrors.of("resource", "", ErrorCode.RESOURCE_NOT_FOUND_PATH.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND_PATH.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiRes<?>> handleGenericNoSuchElementException(NoSuchElementException exception) {
        log.error("Generic NoSuchElementException occurred", exception);
        List<FieldErrors> errors = FieldErrors.of("element", "", ErrorCode.RESOURCE_NOT_FOUND.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(schema = @Schema(implementation = ApiRes.class)))
    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ApiRes<?>> handleIllegalStateException(IllegalStateException exception) {
        List<FieldErrors> errors = FieldErrors.of("token", "", ErrorCode.TOKEN_EXPIRED.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_EXPIRED, ErrorCode.TOKEN_EXPIRED.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiRes<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("HttpMessageNotReadableException occurred", exception.getHttpInputMessage());
        List<FieldErrors> errors = FieldErrors.of("request_body", "", ErrorCode.INVALID_FORMAT.name(), exception.getMessage());
        ApiRes<Object> error = ApiRes.error(ErrorCode.INVALID_FORMAT, ErrorCode.INVALID_FORMAT.getMessage(), errors);
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ApiRes<?>> handleIOException(Exception exception) {
        log.error("IOException occurred", exception.getMessage());
        List<FieldErrors> errors = FieldErrors.of("server", "", ErrorCode.TOKEN_ERROR.name(),exception.getMessage() );
        ApiRes<Object> error = ApiRes.error(ErrorCode.TOKEN_ERROR, ErrorCode.TOKEN_ERROR.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiRes<?>> handleException(Exception exception) {
        log.error("Unexpected error occurred", exception.getMessage());
        List<FieldErrors> errors = FieldErrors.of("server", "", ErrorCode.INTERNAL_SERVER_ERROR.name(), "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        ApiRes<Object> error = ApiRes.error(ErrorCode.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}