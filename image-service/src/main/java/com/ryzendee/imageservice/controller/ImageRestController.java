package com.ryzendee.imageservice.controller;

import com.ryzendee.imageservice.dto.image.response.ImageGetUrlResponse;
import com.ryzendee.imageservice.dto.image.response.ImageSaveResponse;
import com.ryzendee.imageservice.service.ImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SecurityRequirement(name = "keycloak")
@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageRestController {

    private final ImageService imageService;

    @PostMapping
    public ImageSaveResponse saveImage(@RequestParam MultipartFile image) {
        return imageService.saveImage(image);
    }

    @GetMapping("/{objectName}")
    public ImageGetUrlResponse getUrlToImage(@PathVariable String objectName) {
        return imageService.getUrlToImage(objectName);
    }
}
