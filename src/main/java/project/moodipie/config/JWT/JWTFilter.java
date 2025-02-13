package project.moodipie.config.JWT;

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
import java.util.List;

import static project.moodipie.config.JWT.JWTUtil.isExpired;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //제외 url
        String requestURI = request.getRequestURI();
        if (requestURI.matches("/api/(login|signup)") ||
                requestURI.matches("/swagger-ui/.*") ||
                requestURI.matches("/v3/api-docs/.*") ||
                requestURI.matches("/v3/api-docs") ||
                requestURI.equals("/v3/api-docs.yaml")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromCookie(request);  // 쿠키에서 토큰 추출

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing token");
            return;
        }

        String userEmail = getEmailFromToken(token);
        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Malformed token");
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

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
