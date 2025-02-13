package project.moodipie.config.JWT;

import io.jsonwebtoken.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.moodipie.response.error.ErrorCode;

import java.util.Date;

@Component
@Getter
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    private final Long expireMs = 60 * 60 * 1000L; // 1시간

    public static String createJwt(String email, long expiredMs, String secretKey) {
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static boolean isExpired(String token, String secretKey) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public static String getEmailFromToken(String token, String secretKey) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("email", String.class);
    }

    public static String refresh(String token, String secretKey,Long expiredMs) {
        if (isExpired(token, secretKey)) {
            throw new RuntimeException("Token is expired. Please log in again.");
        }
        String email = getEmailFromToken(token, secretKey);
        return createJwt(email, expiredMs, secretKey);
    }

    public void expireByEmail(String userEmail) {
        if (userEmail != null) {

        }
    }
    public static boolean validate(String token, String secretKey) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException| ExpiredJwtException | UnsupportedJwtException e) {
            return false;
        }
    }

}
