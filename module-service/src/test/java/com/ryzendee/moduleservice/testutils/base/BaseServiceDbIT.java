package com.ryzendee.moduleservice.testutils.base;

import com.ryzendee.moduleservice.TestcontainersConfiguration;
import com.ryzendee.moduleservice.testutils.config.TestConfig;
import com.ryzendee.moduleservice.testutils.facade.TestDatabaseFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({TestConfig.class, TestcontainersConfiguration.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class BaseServiceDbIT {

    @Autowired
    protected TestDatabaseFacade testDatabaseFacade;

    @MockBean
    private SecurityFilterChain securityFilterChain;
}
