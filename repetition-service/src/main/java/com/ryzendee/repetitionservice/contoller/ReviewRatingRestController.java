package com.ryzendee.repetitionservice.contoller;

import com.ryzendee.repetitionservice.enums.ReviewRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review-ratings")
public class ReviewRatingRestController {

    @GetMapping
    public List<ReviewRating> getAllReviewRatings() {
        return List.of(ReviewRating.values());
    }
}
