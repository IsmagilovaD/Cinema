package ru.itis.cinema.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.cinema.dto.FilmDto;
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

    @Operation(summary = "Получение отзывов пределенного автора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница отзывов",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ReviewsPage.class)
                            )
                    }
            )
    })
    @GetMapping("/{customer-id}")
    public ResponseEntity<ReviewsPage> getReviews(
            @Parameter(description = "ID автора отзывов") @PathVariable("customer-id") Long customerId,
            @Parameter(description = "Номер страницы") @RequestParam("page") int page){
        return ResponseEntity.ok(reviewService.getReviews(customerId, page));
    }

    @Operation(summary = "Добавление отзыва")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Новой отзыв",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ReviewDto.class)
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<ReviewDto> postReview(
            @Parameter(description = "Новый отзыв") @Valid @RequestBody ReviewForm reviewForm,
                                                Principal principal){
        String customer = principal.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.postReview(reviewForm,customer));
    }
}
