package project.moodipie.config.JWT;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Order(0)
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //필터가 처리 하지 않음.
        String requestURI = request.getRequestURI();
        if (requestURI.matches("/api/(login|signup)") ||
                requestURI.matches("/swagger-ui/.*") ||
                requestURI.matches("/v3/api-docs/.*") ||
                requestURI.equals("/v3/api-docs.yaml")) {
            filterChain.doFilter(request, response);
            return;
        }


        try {
            // 헤더 검증
            String authorization = getAuthorization(request, response, filterChain);
            // Token 추출
            String token = extractToken(authorization);

            // 이메일 추출
            String userEmail = getEmailFromToken(token);

            // 사용자 인증 처리
            setAuthentication(userEmail, token, request);

        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
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
        String token = authorization.split(" ")[1];
        return token;
    }

    private String getEmailFromToken(String token) {
        String userEmail = JWTUtil.getEmailFromToken(token, secretKey);
        if (userEmail == null) {
            throw new MalformedJwtException("잘못된 형식 토큰");
        }
        return userEmail;
    }


    private void setAuthentication(String userEmail, String token, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(userEmail, token, List.of(new SimpleGrantedAuthority("USER")));
        authenticated.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticated);
    }

}