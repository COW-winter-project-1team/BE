package project.moodipie.spotify.configuration.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(SpotifyProperties.class)
@PropertySource(
        "classpath:properties/spotify.properties"
)
public class PropertiesConfiguration {
}
