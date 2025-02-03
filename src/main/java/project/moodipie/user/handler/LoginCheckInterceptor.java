package project.moodipie.user.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println("[interceptor] : " + requestURI);

        if (requestURI.startsWith("/users/login") || requestURI.startsWith("/users/signup") || requestURI.startsWith("/error")) {
            return true;  // 로그인 페이지로의 접근은 인터셉터를 거치지 않음
        }

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("currentuser") == null) {
            // 로그인 되지 않음
            System.out.println("[미인증 사용자 요청]");
            //로그인으로 redirect
            response.sendRedirect("/users/login");
            return false;
        }
        // 로그인 되어있을 떄
        return true;
    }
}
