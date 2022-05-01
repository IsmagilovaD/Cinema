package ru.itis.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cinema.models.Review;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private String authorName;
    private String text;

    public static ReviewDto from(Review review){
        return ReviewDto.builder()
                .authorName(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName())
                .text(review.getText())
                .build();
    }

    public static List<ReviewDto> from(List<Review> reviews){
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }
}
