package com.ryzendee.imageservice.service.helpers.detector;

import com.ryzendee.imageservice.exception.ContentTypeDetectionException;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class TikaContentTypeDetector implements ContentTypeDetector {

    private final Tika tika;
    @Override
    public String detect(InputStream inputStream) {
        try {
            return tika.detect(inputStream);
        } catch (IOException ex) {
            throw new ContentTypeDetectionException("Exception while determining content type", ex);
        }
    }
}
