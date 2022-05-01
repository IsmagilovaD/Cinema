package ru.itis.cinema.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.cinema.dto.FilmsPage;
import ru.itis.cinema.dto.ReviewsPage;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Genre;
import ru.itis.cinema.models.Review;
import ru.itis.cinema.repositories.FilmsRepository;
import ru.itis.cinema.repositories.GenresRepository;
import ru.itis.cinema.repositories.ReviewsRepository;
import ru.itis.cinema.services.FilmService;

import static ru.itis.cinema.dto.FilmDto.from;
import static ru.itis.cinema.dto.ReviewDto.from;

@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final FilmsRepository filmsRepository;
    private final GenresRepository genresRepository;
    private final ReviewsRepository reviewsRepository;

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
    public FilmsPage getFilmsByGenre(int page,String genreName) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Genre genre = genresRepository.findGenreByName(genreName).orElseThrow();
        Page<Film> filmsPage = filmsRepository.findFilmsByGenres(pageRequest,genre);
        return FilmsPage.builder()
                .films(from(filmsPage.getContent()))
                .totalPages(filmsPage.getTotalPages())
                .build();
    }

    @Override
    public ReviewsPage getFilmReviews(int page, Long id) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Film film = filmsRepository.findById(id).orElseThrow();
        Page<Review> reviewsPage =reviewsRepository.findReviewByFilm(pageRequest,film);
        return ReviewsPage.builder()
                .reviews(from(reviewsPage.getContent()))
                .totalPages(reviewsPage.getTotalPages())
                .build();
    }
}
