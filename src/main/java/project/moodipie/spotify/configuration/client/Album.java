package project.moodipie.spotify.configuration.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class Album {

    private String id;
    private String name;
    private String releaseDate;

    public Album() {
    }

    public Album(String id, String name, String releaseDate) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
    }
}
