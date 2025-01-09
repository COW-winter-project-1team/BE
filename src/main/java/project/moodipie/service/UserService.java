package project.moodipie.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.controller.dto.CreateUserRequest;
import project.moodipie.controller.dto.LoginDto;
import project.moodipie.controller.dto.UpdateUserRequest;
import project.moodipie.controller.dto.UserResponse;
import project.moodipie.entity.User;
import project.moodipie.handler.exeption.RestfullException;
import project.moodipie.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Component
@Transactional
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(CreateUserRequest createUserRequest) {
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        createUserRequest.setPassword=encodedPassword;
        User newuser = (User) createUserRequest.toEntity();
        userRepository.save(newuser);
        System.out.println(newuser.getName() + "님이 회원가입 되었습니다.");
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(final Long id) {
        User user = findUserById(id);
        return new UserResponse(user.getName(), user.getEmail());
    }

    public void updateUser(final Long id, UpdateUserRequest updateRequest) {
        User user = findUserById(id);
        user.updateName(updateRequest.getName());
    }

    public void deleteUser(final Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public User login(LoginDto loginDto) {
        User currentuser = userRepository.findByEmail(loginDto.getEmail());

        if (currentuser == null) {
            System.out.println("not found user");
            throw new RestfullException("NOT_FOUND_ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!loginDto.getPassword().equals(currentuser.getPassword())) {
            System.out.println(loginDto.getPassword()+" : "+ currentuser.getPassword());
            throw new RestfullException("WRONG_PASSWORD", HttpStatus.BAD_REQUEST);
        }

        return currentuser;
    }
}
