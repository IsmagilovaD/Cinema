package ru.itis.cinema.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Genre;

public interface FilmsRepository extends JpaRepository<Film, Long> {
    Page<Film> findFilmsByGenres(Pageable pageable, Genre genre);
}