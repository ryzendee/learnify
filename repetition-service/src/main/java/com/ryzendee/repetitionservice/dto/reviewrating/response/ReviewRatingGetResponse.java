package com.ryzendee.repetitionservice.dto.reviewrating.response;

import com.ryzendee.repetitionservice.enums.ReviewRating;

import java.util.List;

public record ReviewRatingGetResponse(List<ReviewRating> reviewRatings) {
}
