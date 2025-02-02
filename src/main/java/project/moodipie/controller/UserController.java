package project.moodipie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.moodipie.dto.CreateUserRequest;
import project.moodipie.dto.LoginDto;
import project.moodipie.dto.SessionUser;
import project.moodipie.dto.UpdateUserRequest;
import project.moodipie.dto.response.SignUpResponse;
import project.moodipie.dto.response.UserResponse;
import project.moodipie.entity.User;
import project.moodipie.handler.exeption.RestfullException;
import project.moodipie.repository.UserRepository;
import project.moodipie.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;
    private final UserRepository userRepository;
    @Operation(summary = "마이페이지 조회", description = "내 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공"),
    })
    @GetMapping("/users")//마이페이지
    public UserResponse userPage() {
        SessionUser currentUser = getSessionUser();
        UserResponse response = new UserResponse(currentUser.getUsername(), currentUser.getPicture());
        return response;
    }
    @Operation(summary = "내 정보 수정", description = "내 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    @PutMapping("/users")//회원정보 수정
    public void updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        SessionUser currentUser = getSessionUser();
        userService.updateUser(currentUser.getEmail(), updateUserRequest);
        User updateduser = userService.findUserByEmail(currentUser.getEmail());
        this.session.setAttribute("currentUser", new SessionUser(updateduser));
    }
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
    })
    @DeleteMapping("/users")//회원탈퇴
    public void deleteUser() {
        SessionUser currentUser = getSessionUser();
        userService.deleteUserByEmail(currentUser.getEmail());
        session.removeAttribute("currentUser");
    }
    @Operation(summary = "회원가입", description = "회원에 가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
    })
    @PostMapping("/signup")//회원가입 기능
    public SignUpResponse signup(@RequestBody CreateUserRequest createUserRequest) {
        return userService.signup(createUserRequest);
    }
    @Operation(summary = "로그인", description = "내 정보로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
    })
    @PostMapping("/login")//로그인
    public void login(@RequestBody LoginDto loginDto) {
        User loginuser = userService.login(loginDto);
        SessionUser currentUser = new SessionUser(loginuser);
        if (loginuser.isFirstLogin()) {//첫 로그인일 때
            session.setAttribute("currentUser", currentUser);
            loginuser.setFirstLogin(false);
            userRepository.save(loginuser);
        } else {
            session.setAttribute("currentUser", currentUser);// 기존 로그인일 때
        }
    }
    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
    })
    @PostMapping("/logout")//로그아웃
    public void logout() {
        session.invalidate();
    }

    
    
    private SessionUser getSessionUser() {
        SessionUser currentUser = (SessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new RestfullException("User is not logged in.", HttpStatus.BAD_REQUEST);
        }
        return currentUser;
    }
}
