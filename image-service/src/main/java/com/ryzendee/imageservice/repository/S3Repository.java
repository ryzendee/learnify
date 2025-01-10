package com.ryzendee.imageservice.repository;

import com.ryzendee.imageservice.dto.s3.S3SaveData;

public interface S3Repository {
    void saveObject(S3SaveData s3SaveData);
    String getUrlToObject(String bucketName, String objectName);

}
