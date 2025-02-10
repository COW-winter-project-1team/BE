package project.moodipie.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Schema(description = "수정된 이름", example = "둘리")
    @NotNull
    public String name;
}
