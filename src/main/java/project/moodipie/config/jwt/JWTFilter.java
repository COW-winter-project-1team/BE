package project.moodipie.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static project.moodipie.config.jwt.JWTUtil.isExpired;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //제외 url
        String requestURI = request.getRequestURI();
        if (requestURI.matches("/api/(login|signup)") ||
                requestURI.matches("/swagger-ui/.*") ||
                requestURI.matches("/v3/.*")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getTokenFromRequest(request);  // 쿠키에서 토큰 추출

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("NULL token");
            return;
        }

        if (!JWTUtil.validate(token, secretKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        }

        String userEmail = getEmailFromToken(token);
        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Email does not exist");
            return;
        }

        if (isExpired(token,secretKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
        }

        setAuthentication(userEmail, token, request);
        filterChain.doFilter(request, response);
    }

    private String getEmailFromToken(String token) {
        String userEmail = JWTUtil.getEmailFromToken(token, secretKey);
        if (userEmail == null) {
            return null;
        }
        return userEmail;
    }

    private void setAuthentication(String userEmail, String token, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(userEmail, token, List.of(new SimpleGrantedAuthority("USER")));
        authenticated.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticated);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        //헤더로 올 때
        String tokenFromCookie = getTokenFromCookie(request);
        if (tokenFromCookie != null) {
            return tokenFromCookie;
        }
        String cookieHeader = request.getHeader("Cookie");
        if (cookieHeader != null) {
            System.out.println("헤더로 왔을 때"+cookieHeader);
            String[] cookies = cookieHeader.split(";");
            for (String cookie : cookies) {
                if (cookie.trim().startsWith("accessToken=")) {
                    return cookie.trim().substring("accessToken=".length());
                }
            }
        }
        return null;
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        //쿠키로 올 때
        if (request.getCookies() != null) {
            System.out.println("쿠키로 왔을 때"+Arrays.toString(request.getCookies()));
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
