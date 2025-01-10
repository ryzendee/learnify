package com.ryzendee.imageservice.service;

import com.ryzendee.imageservice.dto.image.response.ImageGetUrlResponse;
import com.ryzendee.imageservice.dto.image.response.ImageSaveResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageSaveResponse saveImage(MultipartFile multipartFile);
    ImageGetUrlResponse getUrlToImage(String objectName);
}
