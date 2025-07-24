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
import project.moodipie.config.security.UserDetailsImpl;
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

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원관리 CRUD")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "마이페이지 조회", description = "내 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")})
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "조회 실패 - userEmail 오류", description = "userEmail 값이 Null 이라서 플레이리스트 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/users")
    public ResponseEntity<ApiRes<UserInfoResponse>> getUserInfo(
            @Parameter(description = "유저 정보 얻기 위한 이메일")
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoResponse userInfo = userService.getUserInfo(userDetails.getEmail());
        ApiRes<UserInfoResponse> response = ApiRes.ok(userInfo);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "내 정보 수정", description = "내 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "수정 실패 - 필드 에러", description = "필드 조건에 맞지 않아 회원정보 수정에 실패했습니다.", value = ErrorCode.class, constant = "INVALID_FORMAT")
            }
    )
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/users")
    public ResponseEntity<ApiRes<UserInfoResponse>> updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserInfoResponse userInfoResponse = userService.updateUser(userDetails.getEmail(), updateUserRequest);
        ApiRes<UserInfoResponse> response = ApiRes.update(userInfoResponse);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "조회 실패 - userEmail 오류", description = "userEmail 값이 Null 이라서 플레이리스트 조회에 실패했습니다.", value = ErrorCode.class, constant = "NULL_VALUE"),
            }
    )
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/users")
    public ResponseEntity<ApiRes<UserInfoResponse>> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // userDetails에서 이메일을 가져와서 해당 유저를 삭제
        UserInfoResponse user = userService.deleteUserByEmail(userDetails.getEmail());
        ApiRes<UserInfoResponse> response = ApiRes.delete(user);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "회원가입", description = "회원에 가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
    })
    @ApiResponseExplanations(
            errors = {
                    @ApiExceptionExplanation(name = "회원가입 실패 - 필드 에러", description = "필드 조건에 맞지 않아 회원가입에 실패했습니다.", value = ErrorCode.class, constant = "INVALID_FORMAT")
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiRes<CreateUserRequest>> signup(@RequestBody @Valid CreateUserRequest createUserRequest) {
        CreateUserRequest signup = userService.signup(createUserRequest);
        ApiRes<CreateUserRequest> response = ApiRes.created(signup);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "로그인", description = "내 정보로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiRes<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        ApiRes<UserLoginResponse> response = ApiRes.ok(userService.login(userLoginRequest));
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/logout")
    public ResponseEntity<ApiRes<String>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.logout(userDetails.getEmail());
        ApiRes<String> response = ApiRes.ok("로그아웃 성공");
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Operation(summary = "토큰 갱신", description = "만료될 토큰을 갱신합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 Refresh Token"),
            @ApiResponse(responseCode = "403", description = "만료된 Refresh Token")
    })
    @PostMapping("/token")
    public ResponseEntity<ApiRes<Map<String, String>>> refreshToken(@RequestHeader("Authorization-refresh") String refreshHeader) {
        String token = extractBearerToken(refreshHeader);
        String newToken = jwtUtil.refresh(token);

        Map<String, String> tokenData = Map.of(
                "token", newToken,
                "type", "Bearer",
                "expiresIn", String.valueOf(jwtUtil.getExpireMs() / 1000) // 초 단위
        );

        ApiRes<Map<String, String>> response = ApiRes.ok(tokenData);
        return ResponseEntity.ok(response);
    }
    private String extractBearerToken(String header) {
        return header != null && header.startsWith("Bearer ") ? header.split(" ")[1] : null;
    }
}
