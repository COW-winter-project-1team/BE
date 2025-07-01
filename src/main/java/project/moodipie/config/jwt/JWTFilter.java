package project.moodipie.config.jwt;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
import java.util.Optional;
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (isExcludedUrl(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> optionalAuth = getAuthorization(request);
        if (optionalAuth.isEmpty()) {
            sendUnauthorizedResponse(response, "NULL Header or No Bearer");
            return;
        }

        String token = extractToken(optionalAuth.get());
        if (token == null) {
            sendUnauthorizedResponse(response, "NULL token");
            return;
        }

        if (!JWTUtil.validate(token, secretKey)) {
            sendUnauthorizedResponse(response, "Invalid token");
            return;
        }

        if (JWTUtil.isExpired(token, secretKey)) {
            sendUnauthorizedResponse(response, "Token expired");
            return;
        }

        String userEmail = JWTUtil.getEmailFromToken(token, secretKey);
        if (userEmail == null) {
            sendUnauthorizedResponse(response, "Email does not exist");
            return;
        }

        setAuthentication(userEmail, token, request);
        filterChain.doFilter(request, response);
    }

    private boolean isExcludedUrl(String requestURI) {
        return requestURI.matches("/(login|signup)") ||
                requestURI.matches("/swagger-ui/.*") ||
                requestURI.matches("/v3/.*");
    }

    private Optional<String> getAuthorization(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (authorization != null && authorization.startsWith("Bearer "))
                ? Optional.of(authorization)
                : Optional.empty();
    }

    private String extractToken(String authorization) {
        String[] parts = authorization.split(" ");
        return (parts.length == 2) ? parts[1] : null;
    }

    private void setAuthentication(String userEmail, String token, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(userEmail, token, List.of(new SimpleGrantedAuthority("USER")));
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }


}
