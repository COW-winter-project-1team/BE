package project.moodipie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.moodipie.dto.response.HelpResponse;

@RestController
@RequestMapping("/help")
public class HelpController {
    @PostMapping
    public HelpResponse helpPost() {
        return new HelpResponse();
    }
    @GetMapping
    public HelpResponse help() {
        return new HelpResponse();
    }
}
