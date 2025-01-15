package project.moodipie.spotify.configuration.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class Album {

    private String id;
    private String name;
    private List<Artist> artists;
    private List<Image> images;



}
