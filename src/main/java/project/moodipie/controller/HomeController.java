package project.moodipie.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.moodipie.controller.dto.CreatePlaylistRequest;
import project.moodipie.service.HomeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private final HomeService homeService;
    @Autowired
    private HttpSession session;

    @PostMapping
    public void makePlaylsit(@RequestBody CreatePlaylistRequest createPlaylistRequest) {
        homeService.makePlaylist(createPlaylistRequest);
    }

    @GetMapping
    public String home() {
        if (session.getAttribute("currentuser") == null) {
            return "redirect:/users/login";  // 로그인하지 않았다면 로그인 페이지로 리디렉션
        }
        return "home";  // 로그인한 사용자는 홈 페이지로 이동
    }
}