package com.ryzendee.imageservice.service.helpers.detector;

import java.io.InputStream;

public interface ContentTypeDetector {

    String detect(InputStream inputStream);

}
