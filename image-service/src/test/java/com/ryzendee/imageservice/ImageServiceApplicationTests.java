package com.ryzendee.imageservice;

import com.ryzendee.imageservice.testutils.base.BaseTestcontainersTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@ActiveProfiles("test")
@SpringBootTest
class ImageServiceApplicationTests extends BaseTestcontainersTest {

	@BeforeAll
	static void startContainers() {
		minioContainer.start();
		keycloakContainer.start();
	}

	@Test
	void contextLoads() {
	}


}
