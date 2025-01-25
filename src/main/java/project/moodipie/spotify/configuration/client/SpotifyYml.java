package project.moodipie.spotify.configuration.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spotify")
public class SpotifyYml {
    private String grantType;
    private String clientId;
    private String clientSecret;
}
