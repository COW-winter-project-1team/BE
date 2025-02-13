package project.moodipie.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import project.moodipie.user.handler.exception.RestfullException;
import project.moodipie.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Component
@Transactional
public class UserService {
    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secretKey;
    private final Long expireMs =  10 * 60 * 1000L;     //60 * 60 * 1000L은 한 시간입니다.

    public CreateUserRequest signup(CreateUserRequest createUserRequest) {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new RestfullException(HttpStatus.CONFLICT, "해당하는 이메일이 존재합니다.");
        }
        userRepository.save(createUserRequest.toEntity());
        return createUserRequest;
    }

    public UserInfoResponse updateUser(String userEmail, UpdateUserRequest updateRequest) {
        User user = findUserByEmail(userEmail);
        user.updateName(updateRequest);
        return getUserInfo(userEmail);
    }

    public User deleteUserByEmail(String userEmail) {
        User user = findUserByEmail(userEmail); //확인 차
        userRepository.deleteByEmail(userEmail);
        return user;
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User currentuser = findUserByEmail(userLoginRequest.getEmail());
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
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RestfullException(HttpStatus.NOT_FOUND,"해당하는 아이디가 없습니다."));
    }
}
