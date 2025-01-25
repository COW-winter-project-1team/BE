package project.moodipie.music.playlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.UserJpaRepository;
import project.moodipie.entity.User;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.entity.PlaylistTrack;
import project.moodipie.music.playlist.repository.PlaylistTrackRepository;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.playlist.repository.PlaylistRepository;
import project.moodipie.music.track.entity.Track;
import project.moodipie.music.track.repository.TrackRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;
    private final PlaylistTrackRepository playlistTrackRepository;
    private final UserJpaRepository userJpaRepository;

    public void savePlaylist(CreatePlaylistRequest request) {
        User user = userJpaRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 없습니다."));
        Playlist playlist = request.toEntity(user);
        playlistRepository.save(playlist);

        List<Track> tracks = trackRepository.findAllById(request.getTrackIds());
        List<PlaylistTrack> playlistTracks = tracks.stream().map(track -> new PlaylistTrack(playlist, track)).toList();

        playlistTrackRepository.saveAll(playlistTracks);
    }

    public List<PlaylistResponse> findAllPlaylist(Long id) {
        return playlistRepository.findByUserId(id).stream().map(playlist -> PlaylistResponse.from(playlist)).collect(Collectors.toList());
    }

}
