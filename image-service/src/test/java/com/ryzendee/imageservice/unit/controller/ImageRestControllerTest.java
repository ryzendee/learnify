package com.ryzendee.imageservice.unit.controller;

import com.ryzendee.imageservice.controller.ImageRestController;
import com.ryzendee.imageservice.dto.image.response.ImageGetUrlResponse;
import com.ryzendee.imageservice.dto.image.response.ImageSaveResponse;
import com.ryzendee.imageservice.service.ImageService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ImageRestController.class)
public class ImageRestControllerTest {

    private static final String BASE_URI = "/api/v1/images";

    @MockBean
    private ImageService imageService;

    @Autowired
    private MockMvc mockMvc;

    private MockMvcRequestSpecification restAssuredRequest;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.basePath = BASE_URI;
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        restAssuredRequest = RestAssuredMockMvc.given()
                .auth()
                .with(jwt());
    }

    // Using MockMvc because there were issues with serialization and content-type handling in Rest Assured
    @DisplayName("Should return status OK with object name when image is saved successfully")
    @Test
    void saveImage_noCondition_shouldReturnResponseWithObjectNameAndStatusIsOk() throws Exception {
        var mockImageFile = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "dummy content".getBytes()
        );

        var expectedImageSaveResponse = new ImageSaveResponse("objectName");
        when(imageService.saveImage(mockImageFile)).thenReturn(expectedImageSaveResponse);

        mockMvc.perform(
                multipart(BASE_URI)
                        .file(mockImageFile)
                        .with(jwt())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(imageService).saveImage(mockImageFile);
    }

    @DisplayName("Should return status OK with object name when image is saved successfully")
    @Test
    void getUrlToImage_existsImage_shouldReturnUrlWithStatusOk() {
        var objectName = "objectName";
        var expectedImageGetUrlResponse = new ImageGetUrlResponse("imageUrl");

        when(imageService.getUrlToImage(objectName)).thenReturn(expectedImageGetUrlResponse);

        var actualUrl = restAssuredRequest.when()
                .get("/{objectName}", objectName)
                .then()
                .status(HttpStatus.OK)
                .extract()
                .as(ImageGetUrlResponse.class);

        assertThat(actualUrl).isEqualTo(expectedImageGetUrlResponse);
        verify(imageService).getUrlToImage(objectName);
    }
}
