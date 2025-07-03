package project.moodipie.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
@Getter
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire-ms}")
    private Long expireMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwt(String email) {
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validate(String token) {
        return parseToken(token).isPresent();
    }

    public boolean isExpired(String token) {
        return parseToken(token)
                .map(claims -> claims.getExpiration().before(new Date()))
                .orElse(true);
    }

    public String getEmailFromToken(String token) {
        return parseToken(token)
                .map(claims -> claims.get("email", String.class))
                .orElse(null);
    }

    public String refresh(String token) {
        if (isExpired(token)) {
            throw new JwtException("만료된 토큰으로는 갱신할 수 없습니다.");
        }
        String email = getEmailFromToken(token);
        if (email == null) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }
        return createJwt(email);
    }

    private Optional<Claims> parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.of(claims);
        } catch (ExpiredJwtException e) {
            log.debug("Token expired but claims extracted: {}", e.getClaims().getSubject());
            return Optional.of(e.getClaims());
        } catch (JwtException e) {
            log.error("JWT parsing failed", e);
            return Optional.empty();
        }
    }

    public void expireByEmail(String userEmail) {
        log.warn("Token expiration not implemented yet. UserEmail: {}", userEmail);
        // Redis에 사용자의 모든 토큰을 블랙리스트에 추가
        // redisTemplate.opsForSet().add("blacklist:" + userEmail, ...);
    }
    public boolean isTokenBlacklisted(String token) {
        // Redis에서 블랙리스트 체크
        // return redisTemplate.opsForSet().isMember("blacklist", token);
        return false;
    }

}
