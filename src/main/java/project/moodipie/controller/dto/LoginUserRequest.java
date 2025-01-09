package project.moodipie.controller.dto;


import project.moodipie.entity.User;

public class LoginUserRequest {
    private String email;
    private String password;

    public User toEntity(){
        return  User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
