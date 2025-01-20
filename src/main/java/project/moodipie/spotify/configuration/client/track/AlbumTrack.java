package project.moodipie.spotify.configuration.client.track;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import project.moodipie.spotify.configuration.client.Artist;
import project.moodipie.spotify.configuration.client.Image;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AlbumTrack {

    private List<Artist> artists;
    private List<Image> images;



}
