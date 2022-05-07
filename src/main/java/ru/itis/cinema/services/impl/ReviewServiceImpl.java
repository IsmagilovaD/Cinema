package ru.itis.cinema.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.cinema.dto.FilmsPage;
import ru.itis.cinema.dto.ReviewDto;
import ru.itis.cinema.dto.ReviewForm;
import ru.itis.cinema.dto.ReviewsPage;
import ru.itis.cinema.models.Customer;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Review;
import ru.itis.cinema.repositories.CustomerRepository;
import ru.itis.cinema.repositories.FilmsRepository;
import ru.itis.cinema.repositories.ReviewsRepository;
import ru.itis.cinema.services.ReviewService;

import static ru.itis.cinema.dto.ReviewDto.from;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final FilmsRepository filmsRepository;

    private final ReviewsRepository reviewsRepository;

    private final CustomerRepository customerRepository;
    @Value("${blog.default-page-size}")
    private int defaultPageSize;

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Override
    public ReviewsPage getReviews(Long customerId, int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Page<Review> reviews = reviewsRepository.findReviewByCustomer(pageRequest,customer);
        return ReviewsPage.builder()
                .reviews(ReviewDto.from(reviews.getContent()))
                .totalPages(reviews.getTotalPages())
                .build();
    }

    @Override
    public ReviewDto postReview(ReviewForm reviewForm, String customerJWT) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(customerJWT);
        String email = decodedJWT.getClaim("email").asString();
        Customer customer = customerRepository.findByEmail(email).orElseThrow();
        Film film = filmsRepository.findFilmByName(reviewForm.getFilmName()).orElseThrow();
        Review review = Review.builder()
                .customer(customer)
                .film(film)
                .text(reviewForm.getText())
                .build();
        return from(reviewsRepository.save(review));
    }
}
