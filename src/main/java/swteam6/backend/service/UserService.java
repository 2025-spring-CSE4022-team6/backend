package swteam6.backend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swteam6.backend.config.JwtTokenProvider;
import swteam6.backend.dto.request.LoginRequestDto;
import swteam6.backend.dto.response.LoginResponseDto;
import org.springframework.transaction.annotation.Transactional;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.ProfileResponse;
import swteam6.backend.dto.response.SimpleReviewDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.entity.Review;
import swteam6.backend.entity.User;
import swteam6.backend.repository.ReviewRepository;
import swteam6.backend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewRepository reviewRepository;

    //회원가입
    @Transactional
    public UserResponseDto signup(UserSignupDto signupDto) {
        
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        
        User user = signupDto.toEntity(passwordEncoder);
        User savedUser = userRepository.save(user);
        return UserResponseDto.fromEntity(savedUser);
    }

    //로그인
    public LoginResponseDto login(LoginRequestDto req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("존재하지 않는 유저입니다."));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 이메일 또는 비밀번호입니다.");
        }

        String token = jwtTokenProvider.generateToken(user);
        return new LoginResponseDto(token);
    }

    public ProfileResponse getUserProfile(String email) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new EntityNotFoundException("해당 email의 유저를 찾을 수 없습니다."));

        List<Review> reviews=reviewRepository.findAllByUser(user);
        List<SimpleReviewDto> simpleReviewDtoList=reviews.stream()
                .map(review->SimpleReviewDto.of(review))
                .toList();

        return ProfileResponse.of(user,simpleReviewDtoList);
    }
}
