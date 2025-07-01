package project.moodipie.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.moodipie.config.jwt.JWTUtil;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.swagger.ApiExceptionExplanation;
import project.moodipie.swagger.ApiResponseExplanations;
import project.moodipie.user.controller.dto.request.CreateUserRequest;
import project.moodipie.user.controller.dto.request.UpdateUserRequest;
import project.moodipie.user.controller.dto.request.UserLoginRequest;
import project.moodipie.user.controller.dto.response.UserInfoResponse;
import project.moodipie.user.controller.dto.response.UserLoginResponse;
import project.moodipie.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원관리 CRUD")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "마이페이지 조회", description = "내 정보를 조회합니다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "이름 : 김무디, 프로필 사진 : moody")})
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "조회 실패 - userEmail 오류", description = "userEmail 값이 Null 이라서 플레이리스트 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/users")
    public ResponseEntity<ApiRes<UserInfoResponse>> userPage(
            @Parameter(description = "유저 정보 얻기 위한 이메일")
            @AuthenticationPrincipal String userEmail) {
        UserInfoResponse userInfo = userService.getUserInfo(userEmail);
        ApiRes<UserInfoResponse> response = ApiRes.ok(userInfo);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "내 정보 수정", description = "내 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "수정 실패 - 필드 에러", description = "필드 조건에 맞지 않아 회원정보 수정에 실패했습니다.", value = ErrorCode.class, constant = "INVALID_FORMAT")
            }
    )
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/users")
    public ResponseEntity<ApiRes<UserInfoResponse>> updateUser(
            @AuthenticationPrincipal String userEmail,
            @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserInfoResponse userInfoResponse = userService.updateUser(userEmail, updateUserRequest);
        ApiRes<UserInfoResponse> response = ApiRes.update(userInfoResponse);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "조회 실패 - userEmail 오류", description = "userEmail 값이 Null 이라서 플레이리스트 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/users")
    public ResponseEntity<ApiRes<UserInfoResponse>> deleteUser(@AuthenticationPrincipal String userEmail) {
        jwtUtil.expireByEmail(userEmail); //Redis 써야함
        UserInfoResponse user = userService.deleteUserByEmail(userEmail);
        ApiRes<UserInfoResponse> response = ApiRes.delete(user);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "회원가입", description = "회원에 가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "회원가입 실패 - 필드 에러", description = "필드 조건에 맞지 않아 회원가입에 실패했습니다.", value = ErrorCode.class, constant = "INVALID_FORMAT")
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiRes<CreateUserRequest>> signup(@Valid @RequestBody CreateUserRequest createUserRequest) {
        CreateUserRequest signup = userService.signup(createUserRequest);
        ApiRes<CreateUserRequest> response = ApiRes.created(signup);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "로그인", description = "내 정보로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
    })
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal String userEmail) {
        jwtUtil.expireByEmail(userEmail);
        return ResponseEntity.ok("로그아웃");
    }

    @Operation(summary = "토큰 갱신", description = "만료될 토큰을 갱신합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
    })
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String token) {
        String newToken = JWTUtil.refresh(token.split(" ")[1], jwtUtil.getSecretKey(), jwtUtil.getExpireMs());
        return ResponseEntity.ok(newToken);
    }
}
