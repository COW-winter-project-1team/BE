package project.moodipie.spotify.configuration.client.track;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class GetTrack {
    private String id;
    private String name;
    private AlbumTrack album;
}
