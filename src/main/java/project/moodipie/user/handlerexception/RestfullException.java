package project.moodipie.user.handlerexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class RestfullException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public RestfullException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}