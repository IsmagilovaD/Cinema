package ru.itis.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Genre;

import java.util.Optional;

public interface GenresRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByName(String name);
}
