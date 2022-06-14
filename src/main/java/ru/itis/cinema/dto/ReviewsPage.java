package ru.itis.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Страница со списком отзывов и общее количество страниц")
public class ReviewsPage {

    private List<ReviewDto> reviews;
    @Schema(description = "Количество доступных страниц")
    private Integer totalPages;
}
