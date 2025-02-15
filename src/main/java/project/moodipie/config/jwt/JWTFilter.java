package project.moodipie.config.jwt;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static project.moodipie.config.jwt.JWTUtil.isExpired;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //필터가 처리 하지 않음.
        String requestURI = request.getRequestURI();
        if (requestURI.matches("/api/(login|signup)") ||
                requestURI.matches("/swagger-ui/.*") ||
                requestURI.matches("/v3/.*")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorization = getAuthorization(request, response, filterChain);

        // Token 추출
        String token = extractToken(authorization);
        if (!JWTUtil.validate(token, secretKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        }
        // 이메일 추출
        String userEmail = getEmailFromToken(token);
        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Email does not exist");
            return;
        }
        // 만료확인
        if (isExpired(token,secretKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
        }
        //사용자 인증 처리
        setAuthentication(userEmail, token, request);

        filterChain.doFilter(request, response);
    }

    private static @NotNull String getAuthorization(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }
        return authorization;
    }

    private String extractToken(String authorization) {
        return authorization.split(" ")[1];
    }

    private String getEmailFromToken(String token) {
        return JWTUtil.getEmailFromToken(token, secretKey);
    }

    private void setAuthentication(String userEmail, String token, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(userEmail, token, List.of(new SimpleGrantedAuthority("USER")));
        authenticated.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticated);
    }

}