package project.moodipie.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.config.jwt.JWTUtil;
import project.moodipie.user.controller.dto.request.CreateUserRequest;
import project.moodipie.user.controller.dto.request.UpdateUserRequest;
import project.moodipie.user.controller.dto.request.UserLoginRequest;
import project.moodipie.user.controller.dto.response.UserInfoResponse;
import project.moodipie.user.controller.dto.response.UserLoginResponse;
import project.moodipie.user.entity.User;
import project.moodipie.user.exception.EmailAlreadyExistsException;
import project.moodipie.user.exception.PasswordMismatchException;
import project.moodipie.user.exception.UserNotFoundException;
import project.moodipie.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(CreateUserRequest createUserRequest) {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.",createUserRequest.getEmail());
        }
        User newUser = User.builder()
                .name(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();
        userRepository.save(newUser);
    }

    @Transactional
    public UserInfoResponse updateUser(String userEmail, UpdateUserRequest updateRequest) {
        User user = findUserByEmail(userEmail);
        user.updateName(updateRequest);
        return UserInfoResponse.from(user);
    }

    @Transactional
    public void deleteUserByEmail(String userEmail) {
        getUserInfo(userEmail);
        userRepository.deleteByEmail(userEmail);
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User currentuser = findUserByEmail(userLoginRequest.getEmail());
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), currentuser.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = jwtUtil.createJwt(userLoginRequest.getEmail());
        String refreshToken = jwtUtil.createRefreshToken(userLoginRequest.getEmail());

        boolean isFirstLogin = currentuser.isFirstLogin();
        currentuser.setFirstLogin(false);

        return UserLoginResponse.from(currentuser, accessToken, refreshToken, isFirstLogin);
    }

    @Transactional
    public void logout(String userEmail) {
        if (!userRepository.existsByEmail(userEmail)) {
            throw new UserNotFoundException("로그아웃할 사용자를 찾을 수 없습니다: ");
        }
        jwtUtil.expireByEmail(userEmail); // Redis에 블랙리스트 추가 로직 필요
    }

    public UserInfoResponse getUserInfo(String userEmail) {
        User user = findUserByEmail(userEmail);
        return UserInfoResponse.from(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: "));
    }

}
