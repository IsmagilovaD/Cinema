package ru.itis.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewForm {
    @NotBlank(message = "Film name must not be empty")
    private String filmName;
    @NotBlank(message = "Text must not be empty")
    private String text;
}
