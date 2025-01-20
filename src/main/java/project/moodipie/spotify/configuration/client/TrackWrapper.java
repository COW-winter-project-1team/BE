package project.moodipie.spotify.configuration.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrackWrapper {
    private List<Track> items;

    public TrackWrapper(List<Track> items) {
        this.items = items;
    }
}
