package swteam6.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swteam6.backend.exception.UserAlreadyExistsException;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.security.JwtTokenProvider;
import swteam6.backend.dto.request.LoginRequestDto;
import swteam6.backend.dto.response.LoginResponseDto;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.entity.User;
import swteam6.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; //내부 구현만 테스트하고 외부 의존성은 무시

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private ReviewRepository reviewRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        reviewRepository = mock(ReviewRepository.class);
        userService = new UserService(userRepository, passwordEncoder, jwtTokenProvider, reviewRepository);
    }

    @Test
    void signup_shouldReturnUserResponseDto_whenSignupSuccessful() {
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setEmail("test@example.com");
        signupDto.setNickname("Tester");
        signupDto.setPassword("1234");
        signupDto.setProfilePath(null);
        User user = signupDto.toEntity(passwordEncoder);
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto response = userService.signup(signupDto);

        assertNotNull(response);
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Tester", response.getNickname());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void signup_shouldThrowNullPointerException_whenRequiredInfoMissing() {
        // 이메일이 없을 때
        UserSignupDto missingEmail = new UserSignupDto();
        missingEmail.setEmail(null);
        missingEmail.setNickname("Tester");
        missingEmail.setPassword("1234");

        assertThrows(NullPointerException.class,
                () -> userService.signup(missingEmail)
        );

        // 빈 문자열 password일 때도 NPE 발생 확인
        UserSignupDto missingPassword = new UserSignupDto();
        missingPassword.setEmail("a@b.com");
        missingPassword.setNickname("Tester");
        missingPassword.setPassword("");

        assertThrows(NullPointerException.class,
                () -> userService.signup(missingPassword)
        );
    }

    @Test
    void signup_shouldThrowUserAlreadyExistsException_whenEmailAlreadyExists() {
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setEmail("exist@example.com");
        signupDto.setNickname("Tester");
        signupDto.setPassword("1234");

        when(userRepository.existsByEmail("exist@example.com")).thenReturn(true);

        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class,
                () -> userService.signup(signupDto)
        );
        assertEquals("이미 존재하는 이메일입니다.", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldReturnLoginResponseDto_whenCredentialsValid() {
        String email       = "login@example.com";
        String rawPassword = "rawPwd";
        String encodedPwd  = "encodedPwd";
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPwd);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPwd)).thenReturn(true);
        when(jwtTokenProvider.generateToken(user)).thenReturn("jwt-token");

        LoginRequestDto req = new LoginRequestDto();
        req.setEmail(email);
        req.setPassword(rawPassword);

        LoginResponseDto response = userService.login(req);

        assertNotNull(response);
        assertEquals("jwt-token", response.getAccessToken());
        verify(jwtTokenProvider, times(1)).generateToken(user);
    }
}