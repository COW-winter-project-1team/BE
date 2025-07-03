package project.moodipie.config.jwt;

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
import project.moodipie.config.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.debug("Processing request: {} {}", request.getMethod(), requestURI);

        if (isExcludedUrl(requestURI)) {
            log.debug("Skipping JWT filter for excluded URL: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractTokenFromHeader(request);
        if (token == null) {
            log.warn("Authorization header missing or malformed for URI: {}", request.getRequestURI());
            sendUnauthorizedResponse(response, "NULL token");
            return;
        }

        if (!jwtUtil.validate(token)) {
            sendUnauthorizedResponse(response, "Invalid token");
            return;
        }

        if (jwtUtil.isExpired(token)) {
            sendUnauthorizedResponse(response, "Token expired");
            return;
        }

        String userEmail = jwtUtil.getEmailFromToken(token);
        if (userEmail == null) {
            sendUnauthorizedResponse(response, "Email does not exist");
            return;
        }

        setAuthentication(userEmail, request);
        log.debug("Authentication set for user: {}", userEmail);

        filterChain.doFilter(request, response);
    }

    private boolean isExcludedUrl(String requestURI) {
        return requestURI.matches("/(login|signup)") ||
                requestURI.matches("/swagger-ui/.*") ||
                requestURI.matches("/v3/.*");
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        String token = authorization.substring(7);
        return token.trim().isEmpty() ? null : token;
    }

    private void setAuthentication(String userEmail, HttpServletRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userEmail);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,   //Crendentials
                        userDetails.getAuthorities()
                );
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }


}