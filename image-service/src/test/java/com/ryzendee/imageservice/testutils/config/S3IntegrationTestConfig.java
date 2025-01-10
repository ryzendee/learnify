package com.ryzendee.imageservice.testutils.config;

import com.ryzendee.imageservice.repository.MinioS3Repository;
import com.ryzendee.imageservice.repository.S3Repository;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class S3IntegrationTestConfig {
    
    @Bean
    public S3Repository s3Repository(MinioClient minioClient) {
        return new MinioS3Repository(minioClient);
    }

    @Bean
    public MinioClient minioClient(@Value("${s3.connection.minio.endpoint}") String endpoint,
                                   @Value("${s3.connection.minio.username}") String username,
                                   @Value("${s3.connection.minio.password}") String password) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(username, password)
                .build();
    }
}
