package swteam6.backend.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;

@Component
public class JwtKeyProvider {

    private Key key;

    /**
     * 애플리케이션 구동 시 한 번만 실행되어
     * HS256에 안전한 256비트 키를 생성합니다.
     * 안전하지 않은 키를 사용할 시에 서버 내부 오류가 발생합니다.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public Key getKey() {
        return key;
    }
}