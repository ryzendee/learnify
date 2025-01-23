package com.ryzendee.repetitionservice;

import com.ryzendee.repetitionservice.testutils.base.BaseTestcontainersTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class RepetitionServiceApplicationTests extends BaseTestcontainersTest {

	@BeforeAll
	static void startupContainers() {
		postgresContainer.start();
		kafkaContainer.start();
	}

	@Test
	void contextLoads() {
	}

}
