package swteam6.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import swteam6.backend.service.*;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {

	private final PlaceService placeService;
	private final ReviewService reviewService;
	private final UserService userService;

	@Autowired
	public BackendApplicationTests(PlaceService placeService,
								   ReviewService reviewService,
								   UserService userService) {
		this.placeService = placeService;
		this.reviewService = reviewService;
		this.userService = userService;
	}

	@Test
	void contextLoads() {
	}
}
