package project.moodipie.music.playlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.music.playlist.controller.dto.request.CreatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.request.UpdatePlaylistRequest;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.controller.dto.response.PlaylistTrackResponse;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.playlist.entity.PlaylistTrack;
import project.moodipie.music.playlist.repository.PlaylistRepository;
import project.moodipie.music.playlist.repository.PlaylistTrackRepository;
import project.moodipie.music.track.controller.dto.response.TrackResponse;
import project.moodipie.music.track.entity.Track;
import project.moodipie.music.track.repository.TrackRepository;
import project.moodipie.user.entity.User;
import project.moodipie.user.repository.UserRepository;

import java.util.ArrayList;
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
    private final UserRepository userRepository;

    public CreatePlaylistRequest savePlaylist(String userEmail, CreatePlaylistRequest request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 없습니다."));
        Long playlistNumber = getNextPlaylistNumber(user.getId());
        Playlist playlist = request.toEntity(user, playlistNumber);
        playlistRepository.save(playlist);
        List<Track> tracks = trackRepository.findAllById(request.getTrackIds());
        List<PlaylistTrack> playlistTracks = new ArrayList<>();
        long playlistTrackId = 0L;
        for (Track track : tracks) {
            PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track, user, ++playlistTrackId);
            playlistTracks.add(playlistTrack);
        }

        return request;

    }

    @Transactional(readOnly = true)
    public List<PlaylistResponse> findAllPlaylistByUserId(Long userId) {
        return playlistRepository.findByUserId(userId).stream().map(PlaylistResponse::from).collect(Collectors.toList());
    }

    public PlaylistResponse deletePlaylist(String userEmail, Long playlistNumber) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        PlaylistResponse deletePlaylist = PlaylistResponse.from(playlistRepository.getReferenceByUserIdAndPlaylistNumber(user.orElseThrow().getId(), playlistNumber));
        playlistRepository.deleteByUserIdAndPlaylistNumber(user.orElseThrow().getId(), playlistNumber);
        return deletePlaylist;
    }

    public PlaylistTrackResponse findPlaylistByUserIdAndPlaylistNumber(String userEmail, Long playlistNumber) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        PlaylistResponse playlist = PlaylistResponse.from(playlistRepository.getReferenceByUserIdAndPlaylistNumber(user.orElseThrow().getId(), playlistNumber));
        List<TrackResponse> tracks = playlistTrackRepository.findByPlaylistId(playlist.getPlaylistNumber())
                .stream()
                .map(playlistTrack -> TrackResponse.from(playlistTrack.getTrack()))
                .collect(Collectors.toList());

        return PlaylistTrackResponse.from(playlist, tracks);
    }

    public UpdatePlaylistRequest updatePlaylist(String userEmail, Long playlistNumber, UpdatePlaylistRequest updatePlaylistRequest) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        Playlist playlist = playlistRepository.getReferenceByUserIdAndPlaylistNumber(user.orElseThrow().getId(), playlistNumber);
        playlist.update(updatePlaylistRequest);
        return updatePlaylistRequest;
    }

    public List<PlaylistTrack> deletePlaylistTrack(String userEmail, Long playlistNumber, Long trackId) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        List<PlaylistTrack> playlistTracks = playlistTrackRepository.findByPlaylistUserIdAndPlaylistPlaylistNumber(user.orElseThrow().getId(), playlistNumber);
        for (PlaylistTrack playlistTrack : playlistTracks) {
            if (playlistTrack.getPlaylistTrackId().equals(trackId))
                playlistTrackRepository.deleteByPlaylistTrackIdAndPlaylist_PlaylistNumberAndUser_Id(trackId, playlistNumber, user.orElseThrow().getId());
        }
        return playlistTracks;
    }

    private Long getNextPlaylistNumber(Long userId) {
        Long playlistCount = playlistRepository.countByUserId(userId);
        return playlistCount + 1;
    }

}
