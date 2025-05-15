package swteam6.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swteam6.backend.config.JwtTokenProvider;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.entity.User;
import swteam6.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; //내부 구현만 테스트하고 외부 의존성은 무시

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        userService = new UserService(userRepository, passwordEncoder, jwtTokenProvider);
    }

    @Test
    void signup_shouldReturnUserResponseDto_whenSignupSuccessful() {
        // given
        UserSignupDto signupDto = new UserSignupDto();
        signupDto.setEmail("test@example.com");
        signupDto.setNickname("Tester");
        signupDto.setPassword("1234");
        signupDto.setProfilePath(null);
        User user = signupDto.toEntity(passwordEncoder);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        UserResponseDto response = userService.signup(signupDto);

        // then
        assertEquals("test@example.com", response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }
}