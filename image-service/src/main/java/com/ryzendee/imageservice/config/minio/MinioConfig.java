package com.ryzendee.imageservice.config.minio;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(MinioConfigurationProperties props) {
        return MinioClient.builder()
                .endpoint(props.endpoint())
                .credentials(props.username(), props.password())
                .build();
    }

}
