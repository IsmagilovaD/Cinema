package ru.itis.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для публикации отзыва")
public class ReviewForm {

    @Schema(description = "Название фильма", example = "THE BATMAN")
    @NotBlank(message = "Film name must not be empty")
    private String filmName;

    @Schema(description = "Текст отзыва", example = "Very spectacular film. I liked it!")
    @NotBlank(message = "Text must not be empty")
    private String text;
}
