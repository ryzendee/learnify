package com.ryzendee.moduleservice.testutils.base;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

public abstract class BaseTestcontainersTest {

    @ServiceConnection
    protected static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.6"));

    protected  static KeycloakContainer keycloakContainer =
            new KeycloakContainer("quay.io/keycloak/keycloak:25.0.2")
                    .withDebug()
                    .withStartupTimeout(Duration.ofMinutes(5))
                    .withReuse(true);

    @DynamicPropertySource
    static void configureKeycloakProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloakContainer.getAuthServerUrl() + "/realms/master"
        );
    }
}
