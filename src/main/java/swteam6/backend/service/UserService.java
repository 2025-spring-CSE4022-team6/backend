package swteam6.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.entity.User;
import swteam6.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public UserResponseDto signup(UserSignupDto signupDto) {
        User user = new User(
                null,
                signupDto.getEmail(),
                signupDto.getNickname(),
                signupDto.getProfilePath()
        );

        User savedUser = userRepository.save(user);
        return UserResponseDto.fromEntity(savedUser);
    }
}