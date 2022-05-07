package ru.itis.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.cinema.dto.FilmDto;
import ru.itis.cinema.dto.SessionForm;
import ru.itis.cinema.services.FilmService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/film")
public class AdminFilmController {

    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<FilmDto> addFilm(@RequestBody FilmDto filmDto){
        return ResponseEntity.ok(filmService.addFilm(filmDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable("id") Long id){
        filmService.deleteFilm(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FilmDto> updateFilm(@PathVariable("id") Long id,
                                              @RequestBody FilmDto filmDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(filmService.updateFilm(id, filmDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> getFilm(@PathVariable("id") Long id){
        return ResponseEntity.ok(filmService.getFilm(id));
    }

    @DeleteMapping("/session")
    public ResponseEntity<?> deleteFilmSession(@RequestBody SessionForm sessionForm){
        filmService.deleteSessions(sessionForm);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED).build();
    }
}
