package com.ryzendee.imageservice.service;

import com.ryzendee.imageservice.dto.image.response.ImageGetUrlResponse;
import com.ryzendee.imageservice.repository.S3Repository;
import com.ryzendee.imageservice.dto.image.response.ImageSaveResponse;
import com.ryzendee.imageservice.dto.s3.S3SaveData;
import com.ryzendee.imageservice.exception.GetImageUrlException;
import com.ryzendee.imageservice.exception.S3RepositoryException;
import com.ryzendee.imageservice.exception.SaveImageException;
import com.ryzendee.imageservice.service.helpers.detector.ContentTypeDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final String imageBucketName;
    private final S3Repository s3Repository;
    private final ContentTypeDetector contentTypeDetector;

    public ImageServiceImpl(@Value("${s3.bucket.image.name}") String imageBucketName,
                            S3Repository s3Repository,
                            ContentTypeDetector contentTypeDetector) {
        this.imageBucketName = imageBucketName;
        this.s3Repository = s3Repository;
        this.contentTypeDetector = contentTypeDetector;
    }

    @Override
    public ImageSaveResponse saveImage(MultipartFile imageFile) {
        try {
            log.info("Preparing to save image with name: {}", imageFile.getName());
            String contentType = contentTypeDetector.detect(imageFile.getInputStream());

            if (!isImage(contentType)) {
                throw new IllegalArgumentException("This file is not a image");
            }

            String objectName = saveImage(contentType, imageFile);
            log.info("Image saved with generated object name: {}", objectName);

            return new ImageSaveResponse(objectName);
        } catch (S3RepositoryException | IOException ex) {
            log.error("Failed to save image", ex);
            throw new SaveImageException("Failed to save image. Try again later");
        }
    }

    @Override
    public ImageGetUrlResponse getUrlToImage(String objectName) {
        try {
            log.info("Preparing to get url to image with object name: {}", objectName);
            String imageUrl = s3Repository.getUrlToObject(imageBucketName, objectName);

            return new ImageGetUrlResponse(imageUrl);
        } catch (S3RepositoryException ex) {
            log.error("Failed to get url to image with object name: {}", objectName, ex);
            throw new GetImageUrlException("Failed to get url to image. Try again later");
        }
    }

    private boolean isImage(String contentType) {
        return contentType.startsWith("image/");
    }

    private String saveImage(String contentType, MultipartFile imageFile) throws IOException {
        String objectName = generateObjectName();
        S3SaveData s3SaveData = new S3SaveData(
                this.imageBucketName,
                objectName,
                contentType,
                imageFile.getInputStream()
        );
        s3Repository.saveObject(s3SaveData);

        return objectName;
    }

    private String generateObjectName() {
        return UUID.randomUUID().toString();
    }
}
