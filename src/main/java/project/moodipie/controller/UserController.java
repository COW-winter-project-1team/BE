package project.moodipie.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.moodipie.dto.CreateUserRequest;
import project.moodipie.dto.LoginDto;
import project.moodipie.dto.UpdateUserRequest;
import project.moodipie.dto.response.SignUpResponse;
import project.moodipie.dto.response.UserResponse;
import project.moodipie.entity.User;
import project.moodipie.repository.UserRepository;
import project.moodipie.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private UserRepository userRepository;

    @GetMapping//마이페이지
    public UserResponse userPage(){
        User currentUser =(User) session.getAttribute("currentuser");
        if(currentUser == null){throw new IllegalArgumentException("User not found");}
        UserResponse response =new UserResponse(currentUser.getName(), currentUser.getProfileImage());
        return response;
    }
    @PutMapping//회원정보 수정
    public void update(@RequestBody UpdateUserRequest updateUserRequest){
        User currentuser = (User) session.getAttribute("currentuser");
        userService.updateUser(currentuser.getId(), updateUserRequest);
        User updateduser = userService.findUserById(currentuser.getId());
        this.session.setAttribute("currentuser", updateduser);
    }
    @DeleteMapping//회원탈퇴
    public void deleteUser() {
        User currentuser = (User) session.getAttribute("currentuser");
        userService.deleteUserById(currentuser.getId());
        session.removeAttribute("currentuser");
    }

    @PostMapping("/signup")//회원가입 기능
    public SignUpResponse signup(@RequestBody CreateUserRequest createUserRequest) {
        return userService.signup(createUserRequest);
    }
    @GetMapping("/signup")//회원가입 페이지
    public String signUpPage(){
        return "signup";
    }

    @PostMapping("/login")//로그인
    public void login(@RequestBody LoginDto loginDto) {
        User currentuser = userService.login(loginDto);
        if (currentuser != null && currentuser.isFirstLogin()) {
            session.setAttribute("currentuser", currentuser);
            currentuser.setFirstLogin(false);
            userRepository.save(currentuser);//첫 로그인일 때
        }
        else if(currentuser.getId() != null) {
            session.setAttribute("currentuser", currentuser);// 기존 로그인일 때
        }
        else {
            // 로그인 실패
        }
    }
    @GetMapping("/login") // 로그인 페이지
    public String getlogin() {
        if (session.getAttribute("currentuser") != null) {
            return "homepage"; //return "redirect:/home";
        }
        // 로그인 페이지로 이동
        return "loginpage";
    }
    @PostMapping("/logout")//로그아웃
    public void logout() {
        session.invalidate();
    }
}
