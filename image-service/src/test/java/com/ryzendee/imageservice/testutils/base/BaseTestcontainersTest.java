package com.ryzendee.imageservice.testutils.base;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;

import java.time.Duration;

@Slf4j
public abstract class BaseTestcontainersTest {

    protected static final MinIOContainer minioContainer = new MinIOContainer("minio/minio:RELEASE.2024-02-17T01-15-57Z.fips")
            .withReuse(true);

    protected static final KeycloakContainer keycloakContainer =
            new KeycloakContainer("quay.io/keycloak/keycloak:25.0.2")
                    .withDebug()
                    .withReuse(true)
                    .withStartupTimeout(Duration.ofMinutes(5));

    @DynamicPropertySource
    static void configureContainers(DynamicPropertyRegistry registry) {
        if (minioContainer.isRunning()) {
            configureMinioContainer(registry);
        }

        if (keycloakContainer.isRunning()) {
            configureKeycloakContainer(registry);
        }
    }
    protected static void configureKeycloakContainer(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloakContainer.getAuthServerUrl() + "/realms/master"
        );
    }

    protected static void configureMinioContainer(DynamicPropertyRegistry registry) {
        registry.add("s3.connection.minio.endpoint", minioContainer::getS3URL);
        registry.add("s3.connection.minio.username", minioContainer::getUserName);
        registry.add("s3.connection.minio.password", minioContainer::getPassword);
    }

}
