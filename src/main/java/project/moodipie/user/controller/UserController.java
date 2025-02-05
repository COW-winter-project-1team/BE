package project.moodipie.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.moodipie.config.JWTUtil;
import project.moodipie.user.controller.dto.request.CreateUserRequest;
import project.moodipie.user.controller.dto.request.UserLoginRequest;
import project.moodipie.user.controller.dto.request.UpdateUserRequest;
import project.moodipie.user.controller.dto.response.UserServiceResponse;
import project.moodipie.user.controller.dto.response.UserLoginResponse;
import project.moodipie.user.controller.dto.response.UserInfoResponse;
import project.moodipie.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name="회원", description ="회원관리 CRUD")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "마이페이지 조회", description = "내 정보를 조회합니다.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "이름 : 김무디, 프로필 사진 : moody") })
    @GetMapping("/users")
    public ResponseEntity<UserInfoResponse> userPage(@RequestHeader("Authorization") String token) {
        String userEmail = JWTUtil.getEmailFromToken(token.split(" ")[1],jwtUtil.getSecretKey());
        return ResponseEntity.ok(userService.getUserInfo(userEmail));
    }
    @Operation(summary = "내 정보 수정", description = "내 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    @PutMapping("/users")
    public ResponseEntity<UserServiceResponse> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateUserRequest updateUserRequest) {
        String userEmail = JWTUtil.getEmailFromToken(token.split(" ")[1],jwtUtil.getSecretKey());
        return ResponseEntity.ok(userService.updateUser(userEmail,updateUserRequest));
    }
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
    })
    @DeleteMapping("/users")
    public ResponseEntity<UserServiceResponse> deleteUser(@RequestHeader("Authorization") String token) {
        String userEmail = JWTUtil.getEmailFromToken(token.split(" ")[1],jwtUtil.getSecretKey());
        jwtUtil.expire(token); // 기능이 안됨
        return ResponseEntity.ok(userService.deleteUserByEmail(userEmail));
    }

    @Operation(summary = "회원가입", description = "회원에 가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
    })
    @PostMapping("/signup")
    public ResponseEntity<UserServiceResponse> signup(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.signup(createUserRequest));
    }

    @Operation(summary = "로그인", description = "내 정보로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        jwtUtil.expire(token);
        return ResponseEntity.ok("로그아웃");
    }

    @Operation(summary = "토큰 갱신", description = "만료된 토큰을 갱신합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
    })
    @PostMapping("/token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String token) {
        String newToken = JWTUtil.refresh(token.split(" ")[1], jwtUtil.getSecretKey(), jwtUtil.getExpireMs());
        return ResponseEntity.ok(newToken);
    }
}
