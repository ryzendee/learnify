package com.ryzendee.imageservice.unit.service;

import com.ryzendee.imageservice.dto.s3.S3SaveData;
import com.ryzendee.imageservice.exception.GetImageUrlException;
import com.ryzendee.imageservice.exception.S3RepositoryException;
import com.ryzendee.imageservice.exception.SaveImageException;
import com.ryzendee.imageservice.repository.S3Repository;
import com.ryzendee.imageservice.service.ImageServiceImpl;
import com.ryzendee.imageservice.service.helpers.detector.ContentTypeDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    private ImageServiceImpl imageService;
    @Mock
    private S3Repository s3Repository;

    @Mock
    private ContentTypeDetector contentTypeDetector;

    private MultipartFile mockMultipartFile;
    private String imageBucketName;

    @BeforeEach
    void setUp() {
        imageBucketName = "image-bucket";
        imageService = new ImageServiceImpl(imageBucketName, s3Repository, contentTypeDetector);
        mockMultipartFile = new MockMultipartFile("file.png", "dummy".getBytes());
    }

    @DisplayName("Should save image successfully")
    @Test
    void saveImage_successCondition_shouldSaveObject() throws IOException {
        when(contentTypeDetector.detect(any(InputStream.class)))
                .thenReturn(MediaType.IMAGE_JPEG_VALUE);

        var imageSaveResponse = imageService.saveImage(mockMultipartFile);
        assertThat(imageSaveResponse.objectName()).isNotNull();

        verify(contentTypeDetector).detect(any(InputStream.class));
        verify(s3Repository).saveObject(any(S3SaveData.class));
    }

    @DisplayName("Should throw SaveImageException when repository throws exception during save")
    @Test
    void saveImage_repositoryThrowsEx_shouldThrowSaveImageException() {
        when(contentTypeDetector.detect(any(InputStream.class)))
                .thenReturn(MediaType.IMAGE_JPEG_VALUE);
        doThrow(S3RepositoryException.class)
                .when(s3Repository).saveObject(any(S3SaveData.class));

        assertThatThrownBy(() -> imageService.saveImage(mockMultipartFile))
                .isInstanceOf(SaveImageException.class)
                .message().isNotBlank();

        verify(s3Repository).saveObject(any(S3SaveData.class));
    }

    @DisplayName("Should throw IllegalArgumentException when file is not an image")
    @Test
    void saveImage_fileIsNotImage_shouldThrowIllegalArgumentEx() {
        when(contentTypeDetector.detect(any(InputStream.class)))
                .thenReturn(MediaType.APPLICATION_JSON_VALUE);

        assertThatThrownBy(() -> imageService.saveImage(mockMultipartFile))
                .isInstanceOf(IllegalArgumentException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should return URL successfully when object name is valid")
    @Test
    void getUrl_successCondition_shouldReturnUrl() {
        var objectName = "objectName";
        var expectedImageUrl = "image-url";

        when(s3Repository.getUrlToObject(imageBucketName, objectName))
                .thenReturn(expectedImageUrl);

        var getImageUrlResponse = imageService.getUrlToImage(objectName);

        assertThat(getImageUrlResponse.imageUrl()).isEqualTo(expectedImageUrl);
        verify(s3Repository).getUrlToObject(imageBucketName, objectName);
    }

    @DisplayName("Should throw GetImageUrlException when repository throws exception during URL retrieval")
    @Test
    void getUrlToImage_repositoryThrowsEx_shouldThrowGetImageUrlException() {
        var objectName = "objectName";

        doThrow(S3RepositoryException.class)
                .when(s3Repository).getUrlToObject(imageBucketName, objectName);

        assertThatThrownBy(() -> imageService.getUrlToImage(objectName))
                .isInstanceOf(GetImageUrlException.class)
                .message().isNotBlank();

        verify(s3Repository).getUrlToObject(imageBucketName, objectName);
    }
}
