package com.ryzendee.imageservice.IT.repository;

import com.ryzendee.imageservice.dto.s3.S3SaveData;
import com.ryzendee.imageservice.exception.S3RepositoryException;
import com.ryzendee.imageservice.repository.S3Repository;
import com.ryzendee.imageservice.testutils.base.BaseTestcontainersTest;
import com.ryzendee.imageservice.testutils.config.S3IntegrationTestConfig;
import io.minio.*;
import io.minio.messages.DeleteObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(S3IntegrationTestConfig.class)
@ExtendWith(SpringExtension.class)
public class S3RepositoryIT extends BaseTestcontainersTest {

    @Autowired
    private S3Repository s3Repository;
    @Autowired
    private MinioClient minioClient;

    private String bucketName;
    private S3SaveData s3SaveData;

    @BeforeAll
    static void startupContainers() {
        minioContainer.start();
    }

    @BeforeEach
    void setUp() throws Exception {
        bucketName = "image-bucket-test";
        s3SaveData = createS3SaveDataWithBucket(bucketName);
        initBucketIfNotExists(bucketName);
        deleteAllFilesInBucket(bucketName);
    }

    @DisplayName("Should save file successfully when bucket exists")
    @Test
    void saveObject_bucketExists_shouldSaveFile() throws Exception {
        s3Repository.saveObject(s3SaveData);

        var getObjectResponse = getObjectFromS3Storage(bucketName, s3SaveData.objectName());
        assertThat(getObjectResponse).isNotNull();
        assertThat(getObjectResponse.bucket()).isEqualTo(bucketName);
        assertThat(getObjectResponse.object()).isEqualTo(s3SaveData.objectName());
    }

    @DisplayName("Should throw S3RepositoryException when bucket does not exist during save")
    @Test
    void saveObject_bucketDoesNotExists_shouldThrowS3RepositoryEx() {
        var s3SaveDataWithNonExistsBucket = createS3SaveDataWithBucket("dummy");
        assertThatThrownBy(() -> s3Repository.saveObject(s3SaveDataWithNonExistsBucket))
                .isInstanceOf(S3RepositoryException.class)
                .message().isNotBlank();
    }

    @DisplayName("Should return URL successfully when object exists")
    @Test
    void getUrlToObject_objectExists_shouldReturnUrl() throws Exception {
        putObject(s3SaveData);

        var urlToObject = s3Repository.getUrlToObject(s3SaveData.bucketName(), s3SaveData.objectName());
        assertThat(urlToObject).isNotNull();
    }

    @DisplayName("Should throw S3RepositoryException when object does not exist")
    @Test
    void getUrlToObject_objectNotExists_shouldThrowS3RepositoryEx() throws Exception {
        deleteAllFilesInBucket(s3SaveData.bucketName());
        assertThatThrownBy(() -> s3Repository.getUrlToObject(s3SaveData.bucketName(), "some file"))
                .isInstanceOf(S3RepositoryException.class);
    }

    @DisplayName("Should throw S3RepositoryException when bucket does not exist during URL retrieval")
    @Test
    void getUrlToObject_bucketDoesNotExists_shouldThrowS3RepositoryEx() {
        assertThatThrownBy(() -> s3Repository.getUrlToObject("dummy", "dummy"))
                .isInstanceOf(S3RepositoryException.class)
                .message().isNotBlank();
    }

    private S3SaveData createS3SaveDataWithBucket(String bucketName) {
        return new S3SaveData(
                bucketName,
                "test-text.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new ByteArrayInputStream("text".getBytes())
        );
    }

    private void initBucketIfNotExists(String bucketName) throws Exception {
        if (!isBucketExists(bucketName)) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        }
    }

    private void deleteAllFilesInBucket(String bucketName) throws Exception {
        var deleteObjectList = getAllObjectsToDeleteInBucket(bucketName);

        minioClient.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket(bucketName)
                        .objects(deleteObjectList)
                        .build()
        );
    }

    private List<DeleteObject> getAllObjectsToDeleteInBucket(String bucketName) throws Exception {
        var iterableResultItem = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .recursive(true)
                        .build()
        );

        var deleteObjectList = new ArrayList<DeleteObject>();
        for (var resultItem : iterableResultItem) {
            var item = resultItem.get();
            deleteObjectList.add(new DeleteObject(item.objectName()));
        }

        return deleteObjectList;
    }

    private GetObjectResponse getObjectFromS3Storage(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    private void putObject(S3SaveData s3SaveData) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(s3SaveData.bucketName())
                        .object(s3SaveData.objectName())
                        .stream(s3SaveData.inputStream(), s3SaveData.inputStream().available(), -1)
                        .build()
        );
    }

    private boolean isBucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );
    }
}
