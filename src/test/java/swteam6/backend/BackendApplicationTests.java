package swteam6.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import swteam6.backend.service.ReviewService;
import swteam6.backend.service.UserService;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {

	@Autowired
	ReviewService reviewService;

	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}
}
