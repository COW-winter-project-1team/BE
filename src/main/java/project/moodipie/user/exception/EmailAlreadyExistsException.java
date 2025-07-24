package project.moodipie.user.exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {
    private final String email;
    public EmailAlreadyExistsException(String message, String email) {
        super(message);
        this.email = email;
    }

}