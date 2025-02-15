package project.moodipie.swagger;

import project.moodipie.response.error.ErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExceptionExplanation {
    Class<ErrorCode> value();
    String constant();

    String name() default "";

    String mediaType() default "application/json";

    String summary() default "";

    String description() default "";

}
