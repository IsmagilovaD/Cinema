package ru.itis.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.cinema.dto.ReviewDto;
import ru.itis.cinema.dto.ReviewForm;
import ru.itis.cinema.dto.ReviewsPage;
import ru.itis.cinema.services.ReviewService;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{customer-id}")
    public ResponseEntity<ReviewsPage> getReviews(@PathVariable("customer-id") Long customerId,
                                                  @RequestParam("page") int page){
        return ResponseEntity.ok(reviewService.getReviews(customerId, page));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> postReview(@Valid @RequestBody ReviewForm reviewForm,
                                                Principal principal){
        String customer = principal.getName();
        return ResponseEntity.ok(reviewService.postReview(reviewForm,customer));
    }
}
