package project.moodipie.spotify.configuration.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrackResponse {
    private TrackWrapper tracks;

    public TrackResponse(TrackWrapper tracks) {
        this.tracks = tracks;
    }
}
