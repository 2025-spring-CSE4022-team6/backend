package swteam6.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import swteam6.backend.controller.ReviewController;
import swteam6.backend.service.ReviewService;

@SpringBootTest
class BackendApplicationTests implements CommandLineRunner {

	@Autowired
	ReviewService reviewService;

	@Test
	void contextLoads() {
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {

	}
}
