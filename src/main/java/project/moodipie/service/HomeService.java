package project.moodipie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.dto.CreatePlaylistRequest;
import project.moodipie.dto.UpdatePlaylistRequest;
import project.moodipie.dto.response.CreatePlaylistResponse;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.repository.PlaylistRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Component
@Transactional
public class HomeService {
    @Autowired
    private final PlaylistRepository playlistRepository;

    public CreatePlaylistResponse makePlaylist(CreatePlaylistRequest createPlaylistRequest) {
        //플리 만들기
        try{
//            Playlist newPlaylist = new Playlist(createPlaylistRequest.getPlaylistTitle().);
//            playlistRepository.save(Playlist);
            return CreatePlaylistResponse.builder()
                    .message("플레이리스트 생성완료")
                    .build();
        }catch (Exception e){
            return CreatePlaylistResponse.builder()
                    .message("플레이리스트 생성실패")
                    .build();
        }

    }
    public void updatePlaylist(UpdatePlaylistRequest updatePlaylistRequest) {
        //플리 수정 로직
        Playlist playlist  = playlistRepository.findById(updatePlaylistRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));
        // 수정된 엔티티 저장
        playlistRepository.save(playlist);
    }
    public List<Playlist> getAllPlaylistsByUserID(Long userId) {
        return playlistRepository.findByUserId(userId);  // UserId에 해당하는 플레이리스트를 반환하는 메서드
    }
    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        playlistRepository.delete(playlist);
    }
}
