package project.moodipie.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.moodipie.dto.CreateUserRequest;
import project.moodipie.dto.LoginDto;
import project.moodipie.dto.SessionUser;
import project.moodipie.dto.UpdateUserRequest;
import project.moodipie.dto.response.SignUpResponse;
import project.moodipie.dto.response.UserResponse;
import project.moodipie.entity.User;
import project.moodipie.handler.exeption.RestfullException;
import project.moodipie.repository.UserRepository;
import project.moodipie.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")//마이페이지
    public UserResponse userPage(){
        SessionUser currentuser = getSessionUser();
        UserResponse response =new UserResponse(currentuser.getUsername(), currentuser.getPicture());
        return response;
    }

    @PutMapping("/users")//회원정보 수정
    public void updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        SessionUser currentuser = getSessionUser();
        userService.updateUser(currentuser.getEmail(), updateUserRequest);
        User updateduser = userService.findUserByEmail(currentuser.getEmail());
        this.session.setAttribute("currentuser", new SessionUser(updateduser));
    }
    @DeleteMapping("/users")//회원탈퇴
    public void deleteUser() {
        SessionUser currentuser = getSessionUser();
        userService.deleteUserByEmail(currentuser.getEmail());
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
        User loginuser  = userService.login(loginDto);
        SessionUser currentuser = new SessionUser(loginuser);
        if (loginuser.isFirstLogin()) {//첫 로그인일 때
            session.setAttribute("currentuser", currentuser);
            loginuser.setFirstLogin(false);
            userRepository.save(loginuser);
        }
        else {
            session.setAttribute("currentuser", currentuser);// 기존 로그인일 때
        }
    }

    @GetMapping("/login") // 로그인 페이지
    public String getlogin() {
        if (session.getAttribute("currentuser") != null) {
            return "home"; //return "redirect:/home";
        }
        // 로그인 페이지로 이동
        return "login";
    }
    @PostMapping("/logout")//로그아웃
    public void logout() {
        session.invalidate();
    }

    private SessionUser getSessionUser() {
        SessionUser currentUser = (SessionUser) session.getAttribute("currentuser");
        if (currentUser == null) {throw new RestfullException("User is not logged in.", HttpStatus.BAD_REQUEST);}
        return currentUser;
    }
}
