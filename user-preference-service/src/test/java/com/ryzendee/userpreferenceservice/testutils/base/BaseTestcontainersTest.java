package com.ryzendee.userpreferenceservice.testutils.base;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class BaseTestcontainersTest {

    @ServiceConnection
    protected static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:8.0.4"));
}
