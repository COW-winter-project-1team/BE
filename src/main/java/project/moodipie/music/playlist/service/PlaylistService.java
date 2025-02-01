package project.moodipie.music.playlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.entity.User;
import project.moodipie.music.playlist.controller.dto.request.UpdatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.entity.PlaylistTrack;
import project.moodipie.music.playlist.repository.PlaylistTrackRepository;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.playlist.repository.PlaylistRepository;
import project.moodipie.music.track.controller.dto.response.TrackResponse;
import project.moodipie.music.track.entity.Track;
import project.moodipie.music.track.repository.TrackRepository;
import project.moodipie.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;
    private final PlaylistTrackRepository playlistTrackRepository;
    private final UserRepository userJpaRepository;

    public void savePlaylist(CreatePlaylistRequest request) {
        User user = userJpaRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 없습니다."));
        Playlist playlist = request.toEntity(user);
        playlistRepository.save(playlist);
        List<Track> tracks = trackRepository.findAllById(request.getTrackIds());
        List<PlaylistTrack> playlistTracks = new ArrayList<>();
        Long playlistTrackId = 0L;
        for (Track track : tracks) {
            PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track, ++playlistTrackId);
            playlistTracks.add(playlistTrack);
        }

        playlistTrackRepository.saveAll(playlistTracks);
    }

    public List<PlaylistResponse> findAllPlaylist(Long id) {
        return playlistRepository.findByUserId(id).stream().map(playlist -> PlaylistResponse.from(playlist)).collect(Collectors.toList());
    }

    public void deletePlaylist(List<Long> ids) {
        playlistRepository.deleteAllById(ids);
    }

    public List<TrackResponse> findPlaylistById(Long id) {
        Playlist playlist = playlistRepository.getReferenceById(id);
        return playlistTrackRepository.getReferenceByPlaylistId(playlist.getId()).stream().map(playlistTrack -> TrackResponse.from(playlistTrack.getTrack())).collect(Collectors.toList());
    }

    public void updatePlaylist(Long id, UpdatePlaylistRequest updatePlaylistRequest) {
        Playlist playlist = playlistRepository.getReferenceById(id);
        playlist.update(updatePlaylistRequest);
    }

    public void deletePlaylistTrack(Long playlistId, Long trackId) {
        List<PlaylistTrack> playlistTracks = playlistTrackRepository.getReferenceByPlaylistId(playlistId);
        for (PlaylistTrack playlistTrack : playlistTracks) {
            if (playlistTrack.getPlaylistTrackId().equals(trackId)) {
                playlistTrackRepository.deleteByPlaylistTrackIdAndPlaylistId(trackId,playlistId);
            }
        }

    }
}
