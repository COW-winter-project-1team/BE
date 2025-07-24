package project.moodipie.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.moodipie.config.security.UserDetailsImpl;
import project.moodipie.response.ApiRes;
import project.moodipie.response.error.ErrorCode;
import project.moodipie.response.error.FieldErrors;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/login", "/signup",
            "/swagger-ui"
    );
    private static final List<String> AUTHENTICATED_PATHS = List.of(
            "^/users$", "^/token$", "^/logout$",
            "^/playlists(/.*)?$", "^/tracks(/.*)?$",
            "^/spotify/api/tracks$"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        if (isExcludedUrl(requestURI) || !requiresAuthentication(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = extractToken(request);
        if (token == null) {
            reject(response, "Authorization", "", "토큰이 없습니다.");
            return;
        }

        if (!jwtUtil.validate(token)) {
            reject(response, "Authorization", token, "유효하지 않은 토큰입니다.");
            return;
        }

        if (jwtUtil.isExpired(token)) {
            reject(response, "Authorization", token, "토큰이 만료되었습니다.");
            return;
        }

        final String email = jwtUtil.getEmailFromToken(token);
        if (email == null) {
            reject(response, "Authorization", token, "토큰에서 이메일을 추출할 수 없습니다.");
            return;
        }

        try {
            setAuthentication(email, request);
        } catch (UsernameNotFoundException e) {
            reject(response, "email", email, "존재하지 않는 사용자입니다.");
            return;
        }

        chain.doFilter(request, response);
    }

    private void setAuthentication(String email, HttpServletRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.debug("SecurityContext 인증 설정 완료: {}", email);
    }

    private String extractToken(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) return null;
        final String token = header.substring(7).trim();
        return token.isEmpty() ? null : token;
    }

    private boolean isExcludedUrl(String uri) {
        return EXCLUDED_PATHS.stream().anyMatch(uri::startsWith);
    }
    private boolean requiresAuthentication(String uri) {
        return AUTHENTICATED_PATHS.stream().anyMatch(pattern -> uri.matches(pattern));
    }

    private void reject(HttpServletResponse response, String field, String value, String reason) throws IOException {
        List<FieldErrors> error = FieldErrors.of(field, value, ErrorCode.TOKEN_ERROR.name(), reason);
        ApiRes<Void> body = ApiRes.error(ErrorCode.UNAUTHORIZED, error);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
