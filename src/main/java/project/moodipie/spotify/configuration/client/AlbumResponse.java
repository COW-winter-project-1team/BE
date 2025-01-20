package project.moodipie.spotify.configuration.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlbumResponse {
    private AlbumWrapper albums;

    public AlbumResponse(AlbumWrapper albums) {
        this.albums = albums;
    }

    public AlbumResponse() {
    }
}
