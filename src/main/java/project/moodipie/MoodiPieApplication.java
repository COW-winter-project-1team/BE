package project.moodipie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import project.moodipie.spotify.configuration.client.SpotifyYml;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
@EnableConfigurationProperties(SpotifyYml.class)
public class MoodiPieApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoodiPieApplication.class, args);
    }

}
