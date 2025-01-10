package com.ryzendee.imageservice.dto.s3;

import java.io.InputStream;

public record S3SaveData(
        String bucketName,
        String objectName,
        String contentType,
        InputStream inputStream
) {
}
