package swteam6.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import swteam6.backend.entity.User;

import java.util.Date;
import java.security.Key;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtKeyProvider keyProvider;

    // 1시간 유효
    private final long validityInMilliseconds = 3600_000;

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        // 필요시 권한 추가: claims.put("role", user.getRole());

        Date now = new Date();
        Date exp = new Date(now.getTime() + validityInMilliseconds);

        Key signingKey = keyProvider.getKey();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(signingKey)
                .compact();
    }
}