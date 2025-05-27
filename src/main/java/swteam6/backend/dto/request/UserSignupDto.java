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


    public User toEntity(UserSignupDto signupDto,PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(signupDto.getEmail())
                .nickname(signupDto.getNickname())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .build();// 인코딩해서 엔티티에 넣음
    }
}