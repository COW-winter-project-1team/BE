package project.moodipie.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.moodipie.user.controller.dto.response.HelpResponse;

@RestController
@RequestMapping("/help")
public class HelpController {
    @Operation(summary = "도움말 페이지에서 넘어가기 ", description = "다음 도움말 페이지를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "다음 도움말 페이지"),
    })
    @PostMapping
    public HelpResponse helpPost() {
        return new HelpResponse();
    }
    
    
    @Operation(summary = "도움말 페이지", description = "첫 로그인 시 도움말 페이지를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "도움말 페이지"),
    })
    @GetMapping
    public HelpResponse help() {
        return new HelpResponse();
    }
}
