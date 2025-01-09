package project.moodipie.controller.dto;

import lombok.Getter;
import lombok.Setter;
import project.moodipie.entity.User;

@Getter
@Setter
public class CreateUserRequest {
    public String setPassword;
    private String username;
    private String email;
    private String password;

    public User toEntity(){
        return  User.builder()
                .name(username)
                .email(email)
                .password(password)
                .build();
    }
}
