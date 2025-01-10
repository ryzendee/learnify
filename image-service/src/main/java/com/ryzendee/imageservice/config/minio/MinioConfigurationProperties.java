package com.ryzendee.imageservice.config.minio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record MinioConfigurationProperties(

        @Value("${s3.connection.minio.username}")
        String username,
        @Value("${s3.connection.minio.password}")
        String password,
        @Value("${s3.connection.minio.endpoint}")
        String endpoint
) {
}
