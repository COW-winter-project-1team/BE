package project.moodipie.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moodipie.user.controller.dto.CreateUserRequest;
import project.moodipie.user.controller.dto.LoginDto;
import project.moodipie.user.controller.dto.UpdateUserRequest;
import project.moodipie.user.controller.dto.response.SignUpResponse;
import project.moodipie.user.entity.User;
import project.moodipie.user.handler.exeption.RestfullException;
import project.moodipie.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Component
@Transactional
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public SignUpResponse signup(CreateUserRequest createUserRequest) {
        User newuser = (User) createUserRequest.toEntity();
        User existingUser = userRepository.findByEmail(createUserRequest.getEmail());
        if (existingUser != null) {
            return SignUpResponse.builder()
                    .message("Email already exists. Please use a different email.")
                    .build();
        }
        userRepository.save(newuser);
        return SignUpResponse.builder()
                .message("Sign up successful. Please login to continue")
                .build();
    }

    public void updateUser(String email, UpdateUserRequest updateRequest) {
        User user = findUserByEmail(email);
        user.updateName(updateRequest.getName());
        user.updateProfilePicture(updateRequest.getProfilePicture());
        userRepository.save(user);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RestfullException("User Not Found",HttpStatus.BAD_REQUEST);
        }
        return user;
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
