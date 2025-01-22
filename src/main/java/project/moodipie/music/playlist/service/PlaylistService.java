package project.moodipie.music.playlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.music.PlaylistTrack;
import project.moodipie.music.PlaylistTrackRepository;
import project.moodipie.music.PlaylistTrackRequest;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.playlist.repository.PlaylistRepository;
import project.moodipie.music.track.entity.Track;
import project.moodipie.music.track.repository.TrackRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;
    private final PlaylistTrackRepository playlistTrackRepository;

    public void savePlaylist(CreatePlaylistRequest request) {
        Playlist playlist = request.toEntity();
        playlistRepository.save(playlist);

        List<Track> tracks = trackRepository.findAllById(request.getTrackIds());
        List<PlaylistTrack> playlistTracks = tracks.stream().map(track -> new PlaylistTrack(playlist, track)).toList();

        playlistTrackRepository.saveAll(playlistTracks);

    }
}
