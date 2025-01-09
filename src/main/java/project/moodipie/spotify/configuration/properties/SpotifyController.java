package project.moodipie.spotify.configuration.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.michaelthelin.spotify.SpotifyApi;

@RestController
@RequestMapping("/spotify/*")
@RequiredArgsConstructor
public class SpotifyController {
    private final SpotifyProperties properties;


    @GetMapping("/test")
    @ResponseBody
    public SpotifyProperties test() {
        return properties;

    }




}
