package ru.itis.cinema.services;

import ru.itis.cinema.dto.FilmDto;
import ru.itis.cinema.dto.FilmsPage;
import ru.itis.cinema.dto.ReviewsPage;
import ru.itis.cinema.dto.SessionsPage;

public interface FilmService {
    FilmsPage getFilms(int page);

    FilmsPage getFilmsByGenre(int page,String genreName);

    ReviewsPage getFilmReviews(int page, Long id);

    SessionsPage getFilmSessions(int page, Long id);

    FilmDto addFilm(FilmDto filmDto);

    void deleteFilm(Long id);

    FilmDto updateFilm(Long id, FilmDto filmDto);

    FilmDto getFilm(Long id);
}
