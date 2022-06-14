package ru.itis.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.cinema.dto.FilmsPage;
import ru.itis.cinema.dto.ReviewsPage;
import ru.itis.cinema.dto.SessionsPage;
import ru.itis.cinema.services.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmsController {

    private final FilmService filmService;

    @Operation(summary = "Получение фильмов с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с фильмами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = FilmsPage.class)
                            )
                    }
            )
    })
    @GetMapping
    public ResponseEntity<FilmsPage> getFilms(
            @Parameter(description = "Номер страницы") @RequestParam("page") int page) {
        return ResponseEntity.ok(filmService.getFilms(page));
    }


    @Operation(summary = "Получение фильмов с определенным жанром с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с фильмами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = FilmsPage.class)
                            )
                    }
            )
    })
    @GetMapping("/genre/{genre-name}")
    public ResponseEntity<FilmsPage> getFilmsByGenre(
            @Parameter(description = "Номер страницы") @RequestParam("page") int page,
            @Parameter(description = "Название жанра") @PathVariable("genre-name") String genreName) {
        return ResponseEntity.ok(filmService.getFilmsByGenre(page, genreName));
    }

    @Operation(summary = "Получение отзывов на определенный фильм")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с отзывами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ReviewsPage.class)
                            )
                    }
            )
    })
    @GetMapping("/{film-id}/review")
    public ResponseEntity<ReviewsPage> getFilmReviews(
            @Parameter(description = "Номер страницы") @RequestParam("page") int page,
            @Parameter(description = "ID фильма") @PathVariable("film-id") Long id) {
        return ResponseEntity.ok(filmService.getFilmReviews(page, id));
    }

    @Operation(summary = "Получение сеансов определенного фильма")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с сеансами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = SessionsPage.class)
                            )
                    }
            )
    })
    @GetMapping("/{film-id}/sessions")
    public ResponseEntity<SessionsPage> getFilmSessions(
            @Parameter(description = "Номер страницы") @RequestParam("page") int page,
            @Parameter(description = "ID фильма") @PathVariable("film-id") Long id) {
        return ResponseEntity.ok(filmService.getFilmSessions(page, id));
    }

}
