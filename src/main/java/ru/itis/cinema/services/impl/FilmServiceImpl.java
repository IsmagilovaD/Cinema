package ru.itis.cinema.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.cinema.dto.*;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Genre;
import ru.itis.cinema.models.Review;
import ru.itis.cinema.models.Session;
import ru.itis.cinema.repositories.FilmsRepository;
import ru.itis.cinema.repositories.GenresRepository;
import ru.itis.cinema.repositories.ReviewsRepository;
import ru.itis.cinema.repositories.SessionRepository;
import ru.itis.cinema.services.FilmService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.itis.cinema.dto.FilmDto.from;
import static ru.itis.cinema.dto.ReviewDto.from;
import static ru.itis.cinema.dto.SessionDto.from;

@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmsRepository filmsRepository;
    private final GenresRepository genresRepository;
    private final ReviewsRepository reviewsRepository;
    private final SessionRepository sessionRepository;

    @Value("${blog.default-page-size}")
    private int defaultPageSize;

    @Override
    public FilmsPage getFilms(int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Film> filmsPage = filmsRepository.findAll(pageRequest);
        return FilmsPage.builder()
                .films(from(filmsPage.getContent()))
                .totalPages(filmsPage.getTotalPages())
                .build();
    }

    @Override
    public FilmsPage getFilmsByGenre(int page, String genreName) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Genre genre = genresRepository.findGenreByName(genreName).orElseThrow();
        Page<Film> filmsPage = filmsRepository.findFilmsByGenres(pageRequest, genre);
        return FilmsPage.builder()
                .films(from(filmsPage.getContent()))
                .totalPages(filmsPage.getTotalPages())
                .build();
    }

    @Override
    public ReviewsPage getFilmReviews(int page, Long id) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Film film = filmsRepository.findById(id).orElseThrow();
        Page<Review> reviewsPage = reviewsRepository.findReviewByFilm(pageRequest, film);
        return ReviewsPage.builder()
                .reviews(from(reviewsPage.getContent()))
                .totalPages(reviewsPage.getTotalPages())
                .build();
    }

    @Override
    public SessionsPage getFilmSessions(int page, Long id) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Film film = filmsRepository.findById(id).orElseThrow();
        Page<Session> sessionsPage = sessionRepository.findSessionByFilm(pageRequest, film);
        return SessionsPage.builder()
                .sessions(from(sessionsPage.getContent()))
                .totalPages(sessionsPage.getTotalPages())
                .build();
    }

    @Override
    public FilmDto addFilm(FilmDto filmDto) {
        List<Genre> genres = filmDto.getGenres().stream()
                .map(x -> genresRepository.findGenreByName(x).orElseThrow())
                .collect(Collectors.toList());
        Film film = Film.builder()
                .name(filmDto.getName())
                .genres(genres)
                .build();
        return from(filmsRepository.save(film));
    }

    @Override
    public void deleteFilm(Long id) {
        Film film = filmsRepository.findById(id).orElseThrow();
        filmsRepository.delete(film);
    }

    @Override
    public FilmDto updateFilm(Long id, FilmDto filmDto) {
        Film film = filmsRepository.findById(id).orElseThrow();
        if (filmDto.getGenres() != null) {
            List<Genre> genres = filmDto.getGenres().stream()
                    .map(x -> genresRepository.findGenreByName(x).orElseThrow())
                    .collect(Collectors.toList());
            film.setGenres(genres);
        }
        if (filmDto.getName() != null) {
            film.setName(filmDto.getName());
        }
        return from(filmsRepository.save(film));
    }

    @Override
    public FilmDto getFilm(Long id) {
        return from(filmsRepository.findById(id).orElseThrow());
    }

    @Transactional
    @Override
    public void deleteSessions(SessionForm sessionForm) {
        if (sessionForm.getFilmName() == null || sessionForm.getFilmName().isBlank())
            sessionRepository.deleteSessionByStartedAtBefore(sessionForm.getStartedAt());
        else {
            sessionRepository.deleteSessionByFilmAndStartedAtBefore(
                    sessionForm.getStartedAt(),
                    filmsRepository.findFilmByName(sessionForm.getFilmName()).orElseThrow());
        }
    }
}
