package project.moodipie.config;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("Authorization = " + authorization);
        if(authorization == null || !authorization.startsWith("Bearer ")){
            logger.error("Authorization 이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if(JWTUtil.isExpired(token,secretKey)){
            logger.error("Token이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String userEmail = JWTUtil.getEmailFromToken(token, secretKey);
        if (userEmail == null) {
            logger.error("Token에서 email을 추출할 수 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEmail, null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}