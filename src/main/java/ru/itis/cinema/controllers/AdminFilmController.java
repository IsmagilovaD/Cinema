package ru.itis.cinema.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.cinema.dto.FilmDto;
import ru.itis.cinema.dto.FilmsPage;
import ru.itis.cinema.dto.SessionForm;
import ru.itis.cinema.dto.SessionsPage;
import ru.itis.cinema.services.FilmService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/film")
public class AdminFilmController {

    private final FilmService filmService;

    @Operation(summary = "Получение информации об определенном фильме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о фильме",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = FilmDto.class)
                            )
                    }
            )
    })
    @GetMapping("/{film-id}")
    public ResponseEntity<FilmDto> getFilm(
           @Parameter(description = "ID фильма") @PathVariable("film-id") Long id){
        return ResponseEntity.ok(filmService.getFilm(id));
    }

    @Operation(summary = "Добавление фильма")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Новый фильм",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = FilmDto.class)
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<FilmDto> addFilm(@RequestBody FilmDto filmDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(filmService.addFilm(filmDto));
    }

    @Operation(summary = "Удаление фильма")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Страница с фильмами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = FilmsPage.class)
                            )
                    }
            )
    })
    @DeleteMapping("/delete/{film-id}")
    public ResponseEntity<FilmsPage> deleteFilm(
            @Parameter(description = "ID удаляемого фильма") @PathVariable("film-id") Long id){
        filmService.deleteFilm(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(filmService.getFilms(0));
    }

    @Operation(summary = "Обновление информации о фильме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Обновленный фильм",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = FilmDto.class)
                            )
                    }
            )
    })
    @PutMapping("/update/{film-id}")
    public ResponseEntity<FilmDto> updateFilm(
            @Parameter(description = "ID обновляемого фильма")@PathVariable("film-id") Long id,
            @Parameter(description = "Новые данные фильма") @RequestBody FilmDto filmDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(filmService.updateFilm(id, filmDto));
    }

    @Operation(summary = "Удаление прошедших сеансов всех или определенного фильма")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Страница с сеансами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = SessionsPage.class)
                            )
                    }
            )
    })
    @DeleteMapping("/session")
    public ResponseEntity<SessionsPage> deleteFilmSessionStartedAtBefore(
           @Parameter(description = "Информация о времени, до которого должны быть удалены сесси" +
                   " (и фильм сессии которого должны быть удалены)") @RequestBody SessionForm sessionForm){
        filmService.deleteSessions(sessionForm);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(filmService.getSessions(0));
    }
}
