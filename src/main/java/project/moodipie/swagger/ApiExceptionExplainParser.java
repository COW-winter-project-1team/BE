package project.moodipie.swagger;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ApiExceptionExplainParser {
    public static void parse(Operation operation, HandlerMethod handlerMethod) {
        ApiResponseExplanations annotation = handlerMethod.getMethodAnnotation(ApiResponseExplanations.class);

        if (annotation != null) {
            generateExceptionResponseDocs(operation, annotation.errors());
        }
    }

    private static void generateExceptionResponseDocs(Operation operation, ApiExceptionExplanation[] exceptions) {
        ApiResponses responses = operation.getResponses();
        Map<Integer, List<ExampleHolder>> holders = Arrays.stream(exceptions)
                .map(ExampleHolder::from)
                .collect(Collectors.groupingBy(ExampleHolder::httpStatus));

        ExampleHolder.addExamplesToResponses(responses, holders);
    }



    @Builder(access = AccessLevel.PRIVATE)
    private record ExampleHolder(int httpStatus, String name, String mediaType, String description, Example holder) {
        static ExampleHolder from(ApiExceptionExplanation annotation) {
            ErrorCode errorCode = ErrorCode.valueOf(annotation.constant());
            return ExampleHolder.builder()
                    .httpStatus(errorCode.getStatus())
                    .name(StringUtils.hasText(annotation.name()) ? annotation.name() : errorCode.getMessage())
                    .mediaType(annotation.mediaType())
                    .description(annotation.description())
                    .holder(createExample(errorCode, annotation.summary(), annotation.description()))
                    .build();
        }

        private static Example createExample(ErrorCode errorCode, String summary, String description) {
            ApiRes<Object> response = ApiRes.error(errorCode);
            Example example = new Example();
            example.setValue(response);
            example.setSummary(summary);
            example.setDescription(description);

            return example;
        }

        private static void addExamplesToResponses(ApiResponses responses, Map<Integer, List<ExampleHolder>> holders) {
            holders.forEach((httpStatus, exampleHolders)->{
                Content content = new Content();
                MediaType mediaType = new MediaType();
                ApiResponse response = new ApiResponse();

                exampleHolders.forEach(holder -> mediaType.addExamples(holder.name(), holder.holder()));
                content.addMediaType("application/json", mediaType);
                response.setContent(content);

                responses.addApiResponse(String.valueOf(httpStatus), response);
            });
        }
    }
}
