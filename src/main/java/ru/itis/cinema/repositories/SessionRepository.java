package ru.itis.cinema.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Session;

public interface SessionRepository extends JpaRepository<Session,Long> {

    Page<Session> findSessionByFilm(Pageable pageable, Film film);
}
