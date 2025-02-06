package project.moodipie.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.config.JWTUtil;
import project.moodipie.user.controller.dto.request.CreateUserRequest;
import project.moodipie.user.controller.dto.request.UpdateUserRequest;
import project.moodipie.user.controller.dto.request.UserLoginRequest;
import project.moodipie.user.controller.dto.response.UserInfoResponse;
import project.moodipie.user.controller.dto.response.UserLoginResponse;
import project.moodipie.user.controller.dto.response.UserServiceResponse;
import project.moodipie.user.entity.User;
import project.moodipie.user.handler.exeption.RestfullException;
import project.moodipie.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Component
@Transactional
public class UserService {
    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secretKey;
    private final Long expireMs =  10 * 60 * 1000L;     //60 * 60 * 1000L은 한 시간입니다.

    public UserServiceResponse signup(CreateUserRequest createUserRequest) {
        User newuser = createUserRequest.toEntity();
        Optional<User> existingUser = userRepository.findByEmail(createUserRequest.getEmail());
        if (existingUser.isPresent()) {
            return UserServiceResponse.builder()
                    .message("해당하는 이메일이 존재합니다.")
                    .build();
        }
        userRepository.save(newuser);
        return UserServiceResponse.builder()
                .message("회원가입 성공, 로그인해주세요")
                .build();
    }

    public UserServiceResponse updateUser(String email, UpdateUserRequest updateRequest) {
        User user = findUserByEmail(email);
        user.updateName(updateRequest.getName());
        user.updateProfilePicture(updateRequest.getProfilePicture());
        userRepository.save(user);
        return UserServiceResponse.builder()
                .message("수정이 완료되었습니다.")
                .build();
    }

    public UserServiceResponse deleteUserByEmail(String userEmail) {
        userRepository.deleteByEmail(userEmail);
        return UserServiceResponse.builder()
                .message("회원 탈퇴가 완료되었습니다.")
                .build();
    }

    public User findUserByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 없습니다."));
        if (user == null) {
            throw new RestfullException("회원을 찾을 수 없습니다.",HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User currentuser = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 없습니다."));
        if (currentuser == null) {
            return new UserLoginResponse("회원을 찾을 수 없습니다.", null);
        }
        if (currentuser.getPassword().equals(userLoginRequest.getPassword())) {
            if (currentuser.isFirstLogin()) {
                currentuser.setFirstLogin(false);
                return new UserLoginResponse("첫 로그인 성공",
                        JWTUtil.createJwt(userLoginRequest.getEmail(), expireMs, secretKey));
            }
            return new UserLoginResponse("로그인 성공",
                    JWTUtil.createJwt(userLoginRequest.getEmail(), expireMs, secretKey));
        }
        return new UserLoginResponse("잘못된 비밀번호입니다.", null);
    }

    public UserInfoResponse getUserInfo(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("해당하는 아이디가 없습니다."));
        return UserInfoResponse.from(user);
    }
}
