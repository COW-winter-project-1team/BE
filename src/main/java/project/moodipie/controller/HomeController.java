package project.moodipie.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.moodipie.dto.CreatePlaylistRequest;
import project.moodipie.dto.response.CreatePlaylistResponse;
import project.moodipie.dto.response.HomeResponse;
import project.moodipie.entity.User;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.service.HomeService;
import project.moodipie.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private final HomeService homeService;
    @Autowired
    private HttpSession session;

    @PostMapping
    public CreatePlaylistResponse makePlaylist(@RequestBody CreatePlaylistRequest createPlaylistRequest) {
        return homeService.makePlaylist(createPlaylistRequest);
    }
    @GetMapping
    public HomeResponse home() {//
        User currentuser = (User) session.getAttribute("currentuser");
        List<Playlist> playlists = homeService.getAllPlaylistsByUserID(currentuser.getId());  // 서비스에서 플레이리스트를 가져옴
        return new HomeResponse(currentuser.getId(),playlists.size(), playlists); // 로그인한 사용자는 홈 페이지로 이동
    }

    @DeleteMapping
    public void deletePlaylist(@RequestParam Long id) {//request type 수정 필요
        homeService.deletePlaylist(id);
    }
    @PostMapping("/logout")
    public void logout() {session.invalidate();}
}