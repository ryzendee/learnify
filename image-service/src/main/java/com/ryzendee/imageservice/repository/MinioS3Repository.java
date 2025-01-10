package com.ryzendee.imageservice.repository;

import com.ryzendee.imageservice.dto.s3.S3SaveData;
import com.ryzendee.imageservice.exception.S3RepositoryException;
import io.micrometer.common.util.StringUtils;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
@RequiredArgsConstructor
public class MinioS3Repository implements S3Repository {

    private static final long PART_SIZE = -1;
    private static final int DEFAULT_EXPIRY_SECONDS = 86400; // 1 day

    private final MinioClient minioClient;

    @Override
    public void saveObject(S3SaveData s3SaveData) {
        try (InputStream inputStream = s3SaveData.inputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(s3SaveData.bucketName())
                            .object(s3SaveData.objectName())
                            .contentType(s3SaveData.contentType())
                            .stream(inputStream, inputStream.available(), PART_SIZE)
                            .build()
            );
        } catch (Exception ex) {
            throw new S3RepositoryException("Failed to save object with name: " + s3SaveData.objectName(), ex);
        }
    }

    @Override
    public String getUrlToObject(String bucketName, String objectName) {
        try {
            if (!isObjectExists(bucketName, objectName)) {
                throw new S3RepositoryException("Object with name " + objectName + " does not exists");
            }

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(DEFAULT_EXPIRY_SECONDS)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception ex) {
            throw new S3RepositoryException("Failed to get url to object with name: " + objectName, ex);
        }
    }

    private boolean isObjectExists(String bucketName, String objectName) throws Exception {
        StatObjectResponse statObjectResponse = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );

        return statObjectResponse != null;
    }
}
