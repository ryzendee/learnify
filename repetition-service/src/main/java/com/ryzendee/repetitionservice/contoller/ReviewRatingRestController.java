package com.ryzendee.repetitionservice.contoller;

import com.ryzendee.repetitionservice.dto.reviewrating.response.ReviewRatingGetResponse;
import com.ryzendee.repetitionservice.enums.ReviewRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review-ratings")
public class ReviewRatingRestController {

    @GetMapping
    public ReviewRatingGetResponse getAllReviewRatings() {
        return new ReviewRatingGetResponse(List.of(ReviewRating.values()));
    }
}
