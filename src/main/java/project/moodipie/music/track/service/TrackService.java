package project.moodipie.music.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.entity.Artist;
import project.moodipie.music.track.entity.Image;
import project.moodipie.music.track.entity.Track;
import project.moodipie.music.track.repository.AlbumRepository;
import project.moodipie.music.track.repository.ArtistRepository;
import project.moodipie.music.track.repository.ImageRepository;
import project.moodipie.music.track.repository.TrackRepository;

import java.util.List;

//@Service
@RequiredArgsConstructor
@Transactional
public class TrackService {
    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;
    private final ImageRepository imageRepository;
    private final ArtistRepository artistRepository;


    public void save(CreateTrackRequest createTrackRequest) {
        Track track = createTrackRequest.toEntity();

    }
}
