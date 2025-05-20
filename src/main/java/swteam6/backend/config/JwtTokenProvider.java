package swteam6.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import swteam6.backend.entity.User;

import java.util.Date;
import java.security.Key;
import java.util.List;

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


    // Authorization 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(keyProvider.getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid token: " + e.getMessage());
        }
        return false;
    }

    // 토큰에서 이메일 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyProvider.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}