package com.ryzendee.moduleservice;

import com.ryzendee.moduleservice.testutils.base.BaseTestcontainersTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
class ModuleServiceApplicationTests extends BaseTestcontainersTest {

    @BeforeAll
    static void startupContainers() {
        postgresContainer.start();
        keycloakContainer.start();
    }

    @Test
    void contextLoads() {
    }

}
