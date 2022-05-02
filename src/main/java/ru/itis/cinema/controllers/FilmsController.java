package ru.itis.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.cinema.dto.FilmsPage;
import ru.itis.cinema.dto.ReviewsPage;
import ru.itis.cinema.dto.SessionsPage;
import ru.itis.cinema.services.FilmService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmsController {

    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<FilmsPage> getFilms(@RequestParam("page") int page){
        return ResponseEntity.ok(filmService.getFilms(page));
    }


    @GetMapping("/genre/{genre-name}")
    public ResponseEntity<FilmsPage> getFilmsByGenre(@RequestParam("page") int page,
                                                     @PathVariable("genre-name") String genreName){
        return ResponseEntity.ok(filmService.getFilmsByGenre(page,genreName));
    }

    @GetMapping("/{id}/review")
    public ResponseEntity<ReviewsPage> getFilmReviews(@RequestParam("page") int page,
                                                      @PathVariable("id") Long id){
        return ResponseEntity.ok(filmService.getFilmReviews(page, id));
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<SessionsPage> getFilmSessions(@RequestParam("page") int page,
                                                        @PathVariable("id") Long id){
        return ResponseEntity.ok(filmService.getFilmSessions(page,id));
    }

}
