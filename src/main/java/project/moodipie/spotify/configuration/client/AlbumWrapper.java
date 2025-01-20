package project.moodipie.spotify.configuration.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class AlbumWrapper {
    private List<Album> items;

    public AlbumWrapper(List<Album> items) {
        this.items = items;
    }

    public AlbumWrapper() {
    }
}