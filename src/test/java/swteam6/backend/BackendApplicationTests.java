package swteam6.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import swteam6.backend.service.ReviewService;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BackendApplicationTests {

	@Autowired
	ReviewService reviewService;

	@Autowired
	UserService userService;

	@Test
	@Transactional
	@DisplayName("UserService - 회원가입 성공 테스트")
	void signup_success() throws Exception {
		// given
		UserSignupDto signupDto = new UserSignupDto();
		signupDto.setEmail("junittest@example.com");
		signupDto.setNickname("JUnit유저");
		signupDto.setProfilePath("/images/test.png");

		// when
		UserResponseDto result = userService.signup(signupDto);

		// then
		assertNotNull(result.getId());
		assertEquals("junittest@example.com", result.getEmail());
		assertEquals("JUnit유저", result.getNickname());
	}

	@Test
	void contextLoads() {
	}
}
