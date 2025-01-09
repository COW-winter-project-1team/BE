package project.moodipie.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.moodipie.controller.dto.*;
import project.moodipie.entity.User;
import project.moodipie.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private HttpSession session;
    @GetMapping
    public String user(){
        if(session.getAttribute("user") == null){
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @PostMapping("/signup")//회원가입 기능
    public String signup(@RequestBody CreateUserRequest createUserRequest) {
        System.out.println("회원가입 작동-controller");
        System.out.println(createUserRequest.getEmail());
        try {
            userService.signup(createUserRequest);
            return "redirect:/users/login";  // 회원가입 성공 후 로그인 페이지로 리디렉션
        } catch (Exception e) {
            e.printStackTrace();  // 예외를 콘솔에 출력하여 어떤 오류가 발생했는지 확인
            return "error";  // 회원가입 실패 시 오류 페이지로 리디렉션
        }
    }
    @GetMapping("/signup")//회원가입 페이지
    public String getPage(){
        return "signup";
    }


    @PutMapping("/name")//이름 수정
    public void update(@RequestBody UpdateUserRequest updateUserRequest){
        User currentuser = (User) session.getAttribute("currentuser");
        userService.updateUser(currentuser.getId(), updateUserRequest);
    }
    @GetMapping("/name")
    public String getNameEditPage(){
        return "nameedit";
    }
    @DeleteMapping//회원 탈퇴
    public void delete(){
        User currentuser = (User) session.getAttribute("currentuser");
        userService.deleteUser(currentuser.getId());
    }


    @PostMapping("/login")//로그인
    public String login(@RequestBody LoginDto loginDto) {
        User currentuser = userService.login(loginDto);
        System.out.println("로그인 작동 "+currentuser.getName());
        if(currentuser.getId() != null) {
            session.setAttribute("currentuser", currentuser);
            return "redirect:/home";
        }else {
            return "error";
        }
    }
    @GetMapping("/login")
    public String getlogin() {
        if (session.getAttribute("currentuser") != null) {
            return "redirect:/home";  // 이미 로그인한 사용자라면 홈 페이지로 리디렉션
        }
        return "login";  // 로그인 페이지로 이동
    }
}
