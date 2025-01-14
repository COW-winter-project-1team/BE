package project.moodipie.spotify.configuration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name="googleClient"
        , url = "http://google.com"
)
public interface GoogleClient {
    @GetMapping
    String helloWorld();
}
