package project.moodipie.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.config.jwt.JWTUtil;
import project.moodipie.user.controller.dto.request.CreateUserRequest;
import project.moodipie.user.controller.dto.request.UpdateUserRequest;
import project.moodipie.user.controller.dto.request.UserLoginRequest;
import project.moodipie.user.controller.dto.response.UserInfoResponse;
import project.moodipie.user.controller.dto.response.UserLoginResponse;
import project.moodipie.user.entity.User;
import project.moodipie.user.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Component
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public CreateUserRequest signup(CreateUserRequest createUserRequest) {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
        userRepository.save(createUserRequest.toEntity());
        return createUserRequest;
    }

    public UserInfoResponse updateUser(String userEmail, UpdateUserRequest updateRequest) {
        User user = findUserByEmail(userEmail);
        user.updateName(updateRequest);
        return getUserInfo(userEmail);
    }

    public UserInfoResponse deleteUserByEmail(String userEmail) {
        UserInfoResponse userInfo = getUserInfo(userEmail);//확인 차
        userRepository.deleteByEmail(userEmail);
        return userInfo;
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User currentuser = findUserByEmail(userLoginRequest.getEmail());
        if (!currentuser.getPassword().equals(userLoginRequest.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        if (currentuser.isFirstLogin()) {
            currentuser.setFirstLogin(false);
            return new UserLoginResponse("첫 로그인 성공",
                    jwtUtil.createJwt(userLoginRequest.getEmail()));
        }
        return new UserLoginResponse("로그인 성공",
                jwtUtil.createJwt(userLoginRequest.getEmail()));
    }

    public UserInfoResponse getUserInfo(String userEmail) {
        User user = findUserByEmail(userEmail);
        return UserInfoResponse.from(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("해당 이메일의 사용자를 찾을 수 없습니다: " + email));
    }

    public String refreshToken(String refreshToken) {
        if (!jwtUtil.validate(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
        if (jwtUtil.isExpired(refreshToken)) {
            throw new IllegalStateException("리프레시 토큰이 만료되었습니다.");
        }
        String email = jwtUtil.getEmailFromToken(refreshToken);
        return jwtUtil.createJwt(email);
    }

    public void logout(String userEmail) {
        if (!userRepository.existsByEmail(userEmail)) {
            throw new NoSuchElementException("로그아웃할 사용자를 찾을 수 없습니다: " + userEmail);
        }
        jwtUtil.expireByEmail(userEmail); // Redis에 블랙리스트 추가 로직 필요
    }
}
