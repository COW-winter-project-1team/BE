package project.moodipie.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.moodipie.user.controller.dto.request.CreateUserRequest;
import project.moodipie.user.controller.dto.request.LoginRequest;
import project.moodipie.user.controller.dto.SessionUser;
import project.moodipie.user.controller.dto.request.UpdateUserRequest;
import project.moodipie.user.controller.dto.response.SignUpResponse;
import project.moodipie.user.controller.dto.response.UserResponse;
import project.moodipie.user.entity.User;
import project.moodipie.user.handler.exeption.RestfullException;
import project.moodipie.user.repository.UserRepository;
import project.moodipie.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name="회원", description ="회원관리 CRUD")
public class UserController {
    private final UserService userService;
    private final HttpSession session;
    private final UserRepository userRepository;

    @Operation(summary = "마이페이지 조회", description = "내 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("/users")
    public UserResponse userPage() {
        SessionUser currentUser = getSessionUser();
        return userService.getUserInfo(currentUser);
    }
    @Operation(summary = "내 정보 수정", description = "내 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    @PutMapping("/users")
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
    @DeleteMapping("/users")
    public void deleteUser() {
        SessionUser currentUser = getSessionUser();
        userService.deleteUserByEmail(currentUser.getEmail());
        session.removeAttribute("currentUser");
    }

    @Operation(summary = "회원가입", description = "회원에 가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
    })
    @PostMapping("/signup")
    public SignUpResponse signup(@RequestBody CreateUserRequest createUserRequest) {
        return userService.signup(createUserRequest);
    }

    @Operation(summary = "로그인", description = "내 정보로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
    })
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        User loginuser = userService.login(loginRequest);
        SessionUser currentUser = new SessionUser(loginuser);
        if (loginuser.isFirstLogin()) {
            session.setAttribute("currentUser", currentUser);
            loginuser.setFirstLogin(false);
            userRepository.save(loginuser);
        } else {
            session.setAttribute("currentUser", currentUser);
        }
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
    })
    @PostMapping("/logout")
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
