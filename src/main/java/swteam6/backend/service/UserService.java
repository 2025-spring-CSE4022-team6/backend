package swteam6.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swteam6.backend.config.JwtTokenProvider;
import swteam6.backend.dto.request.LoginRequestDto;
import swteam6.backend.dto.response.LoginResponseDto;
import org.springframework.transaction.annotation.Transactional;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.entity.User;
import swteam6.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @Transactional
    public UserResponseDto signup(UserSignupDto signupDto) {
        User user = signupDto.toEntity(passwordEncoder);

        User savedUser = userRepository.save(user);
        return UserResponseDto.fromEntity(savedUser);
    }

    //로그인
    public LoginResponseDto login(LoginRequestDto req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("잘못된 이메일 또는 비밀번호입니다."));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 이메일 또는 비밀번호입니다.");
        }

        String token = jwtTokenProvider.generateToken(user);
        return new LoginResponseDto(token);
    }
}