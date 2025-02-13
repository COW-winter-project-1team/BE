package project.moodipie.music.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.music.track.controller.dto.request.CreateTrackRequest;
import project.moodipie.music.track.controller.dto.response.TrackResponse;
import project.moodipie.music.track.entity.Track;
import project.moodipie.music.track.repository.TrackRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TrackService {
    private final TrackRepository trackRepository;


    public List<CreateTrackRequest> save(List<CreateTrackRequest> createTrackRequest) {
        for (CreateTrackRequest trackRequest : createTrackRequest) {
            Track track = trackRequest.toEntity();
            trackRepository.save(track);
        }
        return createTrackRequest;
    }

    public TrackResponse getTrack(String trackId) {
        Track track = trackRepository.getReferenceById(trackId);
        return TrackResponse.from(track);
    }
}
