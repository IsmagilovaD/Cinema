package ru.itis.cinema.services;

import ru.itis.cinema.dto.ReviewDto;
import ru.itis.cinema.dto.ReviewForm;
import ru.itis.cinema.dto.ReviewsPage;

public interface ReviewService {

    ReviewsPage getReviews(Long customerId, int page);

    ReviewDto postReview(ReviewForm reviewForm, String customerJWT);
}
