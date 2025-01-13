package project.moodipie.openfeign;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spotify/*")
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyAuthService spotifyAuthService;

    @GetMapping("/token")
    @ResponseBody
    public String GetToken() {
        return spotifyAuthService.getAccessToken();

    }
}
