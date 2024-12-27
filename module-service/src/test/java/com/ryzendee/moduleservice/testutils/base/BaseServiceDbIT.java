package com.ryzendee.moduleservice.testutils.base;

import com.ryzendee.moduleservice.testutils.config.TestDatabaseFacadeConfig;
import com.ryzendee.moduleservice.testutils.facade.TestDatabaseFacade;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(TestDatabaseFacadeConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class BaseServiceDbIT extends BaseTestcontainersTest {

    @Autowired
    protected TestDatabaseFacade testDatabaseFacade;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @BeforeAll
    static void startupPostgresContainer() {
        postgresContainer.start();
    }
}
