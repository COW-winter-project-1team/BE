package project.moodipie.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new RestfullException(HttpStatus.CONFLICT, "해당하는 이메일이 존재합니다.");
        }
        userRepository.save(createUserRequest.toEntity());
        return UserServiceResponse.builder()
                .message("회원가입 성공, 로그인해주세요")
                .build();
    }

    public UserServiceResponse updateUser(String userEmail, UpdateUserRequest updateRequest) {
        User user = findUserbyEmail(userEmail);
        user.updateName(updateRequest.getName());
        userRepository.save(user);
        return UserServiceResponse.builder()
                .message("수정이 완료되었습니다.")
                .build();
    }

    public UserServiceResponse deleteUserByEmail(String userEmail) {
        User user = findUserbyEmail(userEmail); //확인 차
        userRepository.deleteByEmail(userEmail);
        return UserServiceResponse.builder()
                .message("회원 탈퇴가 완료되었습니다.")
                .build();
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User currentuser = findUserbyEmail(userLoginRequest.getEmail());
        if (!currentuser.getPassword().equals(userLoginRequest.getPassword())) {
            throw new RestfullException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다.");
        }
        if (currentuser.isFirstLogin()) {
            currentuser.setFirstLogin(false);// save 안해도 되나?
            return new UserLoginResponse("첫 로그인 성공",
                    JWTUtil.createJwt(userLoginRequest.getEmail(), expireMs, secretKey));
        }
        return new UserLoginResponse("로그인 성공",
                JWTUtil.createJwt(userLoginRequest.getEmail(), expireMs, secretKey));
    }

    public UserInfoResponse getUserInfo(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RestfullException(HttpStatus.NOT_FOUND,"해당하는 아이디가 없습니다."));
        return UserInfoResponse.from(user);
    }
    public User findUserbyEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RestfullException(HttpStatus.NOT_FOUND,"해당하는 아이디가 없습니다."));
    }
}
