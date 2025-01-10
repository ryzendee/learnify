package com.ryzendee.imageservice.unit.helpers.detector;

import com.ryzendee.imageservice.service.helpers.detector.TikaContentTypeDetector;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TikaContentTypeDetectorTest {

    @InjectMocks
    private TikaContentTypeDetector tikaContentTypeDetector;

    @Mock
    private Tika tika;

    @Test
    void detect_shouldReturnExpectedContentType() throws IOException {
        var mockInputStream = mock(InputStream.class);
        var expectedContentType = MediaType.APPLICATION_JSON.getType();

        when(tika.detect(mockInputStream)).thenReturn(expectedContentType);

        var actualContentType = tikaContentTypeDetector.detect(mockInputStream);

        assertThat(actualContentType).isEqualTo(expectedContentType);
        verify(tika).detect(mockInputStream);
    }
}
