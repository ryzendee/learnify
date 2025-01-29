package com.ryzendee.userpreferenceservice;

import com.ryzendee.userpreferenceservice.testutils.base.BaseTestcontainersTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserNotificationPreferenceServiceApplicationTests extends BaseTestcontainersTest {

	@BeforeAll
	static void startupContainers() {
		mongoDBContainer.start();
	}

	@Test
	void contextLoads() {
	}

}
