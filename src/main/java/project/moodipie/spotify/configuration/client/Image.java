package project.moodipie.spotify.configuration.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Image {
    private String url;
    private Integer height;
    private Integer width;

}
