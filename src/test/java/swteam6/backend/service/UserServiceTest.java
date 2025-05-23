package swteam6.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swteam6.backend.dto.request.LoginRequestDto;
import swteam6.backend.dto.response.LoginResponseDto;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.entity.User;
import swteam6.backend.exception.MissingSignupFieldException;
import swteam6.backend.exception.UserAlreadyExistsException;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import swteam6.backend.security.JwtTokenProvider;

import java.util.MissingFormatArgumentException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; //내부 구현만 테스트하고 외부 의존성은 무시

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        reviewRepository=mock(ReviewRepository.class);
        userService = new UserService(userRepository, passwordEncoder, jwtTokenProvider,reviewRepository);
    }

    @Test
    void signup_shouldReturnUserResponseDto_whenSignupSuccessful() {
        // given
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setEmail("test@example.com");
        signupDto.setNickname("Tester");
        signupDto.setPassword("1234");
        User user = signupDto.toEntity(signupDto,passwordEncoder);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        UserResponseDto response = userService.signup(signupDto);

        // then
        assertEquals("test@example.com", response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void signup_shouldThrowIllegalArgumentException_whenRequiredInfoMissing() {
        // 이메일이 없을 때
        UserSignupDto missingEmail = new UserSignupDto();
        missingEmail.setEmail(null);
        missingEmail.setNickname("Tester");
        missingEmail.setPassword("1234");

        MissingSignupFieldException ex = assertThrows(MissingSignupFieldException.class,
                () -> userService.signup(missingEmail),
                "필수 정보가 누락되면 MissingSignupFiledException을 던져야 합니다."
        );
        verify(userRepository, never()).save(any());

        // 비밀번호가 빈 문자열일 때
        UserSignupDto missingPassword = new UserSignupDto();
        missingPassword.setEmail("a@b.com");
        missingPassword.setNickname("Tester");
        missingPassword.setPassword("");

        MissingSignupFieldException e = assertThrows(MissingSignupFieldException.class,
                () -> userService.signup(missingEmail),
                "필수 정보가 누락되면 MissingSignupFiledException을 던져야 합니다."
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    void signup_shouldThrowIllegalArgumentException_whenEmailAlreadyExists() {
        // given: DB에 이미 동일 이메일이 존재한다고 응답하도록 모킹
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setEmail("exist@example.com");
        signupDto.setNickname("Tester");
        signupDto.setPassword("1234");

        when(userRepository.existsByEmail("exist@example.com")).thenReturn(true);

        // when & then
        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class,
                () -> userService.signup(signupDto)
        );
        assertEquals("이미 존재하는 이메일입니다.", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldReturnLoginResponseDto_whenCredentialsValid() {
        // given
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

        // when
        LoginResponseDto resp = userService.login(req);

        // then
        assertNotNull(resp);
        assertEquals("jwt-token", resp.getAccessToken());
        verify(jwtTokenProvider, times(1)).generateToken(user);
    }
}