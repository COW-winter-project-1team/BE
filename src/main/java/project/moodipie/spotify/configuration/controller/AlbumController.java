package project.moodipie.spotify.configuration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.spotify.configuration.client.*;
import project.moodipie.spotify.configuration.service.AlbumService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spotify/api")
public class AlbumController {

    private final AuthSpotifyClient authSpotifyClient;
    private final AlbumSpotifyClient albumSpotifyClient;
    private final TrackSpotifyClient trackSpotifyClient;
    @Autowired
    public AlbumService albumService;

    public AlbumController(AuthSpotifyClient authSpotifyClient, AlbumSpotifyClient albumSpotifyClient, TrackSpotifyClient trackSpotifyClient) {
        this.authSpotifyClient = authSpotifyClient;
        this.albumSpotifyClient = albumSpotifyClient;
        this.trackSpotifyClient = trackSpotifyClient;
    }

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> helloWorld() {
        return albumService.getalbum();
    }

    @GetMapping("/tracks/{name}/{artist}")
    public ResponseEntity<Track> helloWorld2(@PathVariable("name") String name,       // 곡 제목
                                             @PathVariable("artist") String artist) {
        System.out.println(name+artist);
        return albumService.SearchTrack(name, artist);
    }
}
