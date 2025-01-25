package project.moodipie.handler.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class RestfullException extends RuntimeException {
    private HttpStatus status;

    public RestfullException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
