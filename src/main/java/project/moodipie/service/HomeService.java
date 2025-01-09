package project.moodipie.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.controller.dto.CreatePlaylistRequest;
import project.moodipie.controller.dto.UpdatePlaylistRequest;

@Service
@RequiredArgsConstructor
@Component
@Transactional
public class HomeService {


    public void makePlaylist(CreatePlaylistRequest createPlaylistRequest) {
        //플리 만들기
    }
    public void updatePlaylist(Long id, UpdatePlaylistRequest updatePlaylistRequest) {
        //플리 수정 로직
        //playlistRepository.findById(id);
    }
}
