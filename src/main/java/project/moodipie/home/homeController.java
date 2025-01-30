package project.moodipie.home;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.moodipie.dto.SessionUser;
import project.moodipie.handler.exeption.RestfullException;
import project.moodipie.music.playlist.controller.dto.response.PlaylistResponse;
import project.moodipie.music.playlist.service.PlaylistService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class homeController {

    private final PlaylistService playlistService;
    private final HttpSession session;


    @GetMapping("/home")
    public ResponseEntity<List<PlaylistResponse>> findAllPlaylist() {
        SessionUser currentUser = getSessionUser();
        List<PlaylistResponse> allPlaylist = playlistService.findAllPlaylist(currentUser.getId());
        return ResponseEntity.ok(allPlaylist);
    }

    private SessionUser getSessionUser() {
        SessionUser currentUser = (SessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new RestfullException("User is not logged in.", HttpStatus.BAD_REQUEST);
        }
        return currentUser;
    }
}
