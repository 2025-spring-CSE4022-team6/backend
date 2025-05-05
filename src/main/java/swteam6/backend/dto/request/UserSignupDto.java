package swteam6.backend.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import swteam6.backend.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupDto{

    private String email;
    private String nickname;
    private String password;
    private String profilePath;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return new User(
                null,
                this.email,
                this.nickname,
                passwordEncoder.encode(this.password),  // 🔥 인코딩해서 엔티티에 넣음
                this.profilePath
        );
    }
}