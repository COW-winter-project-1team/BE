package project.moodipie.custom;

import lombok.Getter;

@Getter
public class InvalidFieldFormatException extends RuntimeException {
    private final String fieldName;

    public InvalidFieldFormatException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

}
