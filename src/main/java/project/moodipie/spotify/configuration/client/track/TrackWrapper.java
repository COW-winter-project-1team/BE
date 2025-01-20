package project.moodipie.spotify.configuration.client.track;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import project.moodipie.spotify.configuration.client.Track;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrackWrapper {
    private List<Track> items;

    public TrackWrapper(List<Track> items) {
        this.items = items;
    }
}
