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
@Schema(description = "Страница со списком сеансов и общее количество страниц")
public class SessionsPage {

    private List<SessionDto> sessions;
    @Schema(description = "Количество доступных страниц")
    private Integer totalPages;
}
