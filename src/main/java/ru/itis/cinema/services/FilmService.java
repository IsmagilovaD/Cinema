package ru.itis.cinema.services;

import ru.itis.cinema.dto.FilmsPage;
import ru.itis.cinema.dto.ReviewsPage;

public interface FilmService {
    FilmsPage getFilms(int page);

    FilmsPage getFilmsByGenre(int page,String genreName);

    ReviewsPage getFilmReviews(int page, Long id);
}
