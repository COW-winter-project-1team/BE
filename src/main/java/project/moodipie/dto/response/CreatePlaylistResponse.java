package project.moodipie.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreatePlaylistResponse {
    private String message;
    @Builder
    public CreatePlaylistResponse(String message) {
        this.message = message;
    }
}
