package ru.itis.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Отзыв")
public class ReviewDto {

    @Schema(description = "Название фильма", example = "THE BATMAN")
    private String filmName;
    @Schema(description = "Имя автора", example = "Dinara Ismagilova")
    private String authorName;
    @Schema(description = "текст отзыва", example = "Very spectacular film. I liked it!")
    private String text;

    public static ReviewDto from(Review review){
        return ReviewDto.builder()
                .filmName(review.getFilm().getName())
                .authorName(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName())
                .text(review.getText())
                .build();
    }

    public static List<ReviewDto> from(List<Review> reviews){
        return reviews.stream().map(ReviewDto::from).collect(Collectors.toList());
    }
}
