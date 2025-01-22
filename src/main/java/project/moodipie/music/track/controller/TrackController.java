package project.moodipie.music.track.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.service.TrackService;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping("/save")
    public void createTracks(@RequestBody List<CreateTrackRequest> createTrackRequest) {
        trackService.save(createTrackRequest);
    }
}
