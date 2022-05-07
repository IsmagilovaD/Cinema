package ru.itis.cinema.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Session;

import java.sql.Timestamp;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Page<Session> findSessionByFilm(Pageable pageable, Film film);

    @Modifying
    @Query("delete from Session s where s.startedAt < :startedAt")
    void deleteSessionByStartedAtBefore(@Param("startedAt") Timestamp startedAt);

    @Modifying
    @Query("delete from Session s where s.startedAt < :startedAt and s.film = :film")
    void deleteSessionByFilmAndStartedAtBefore(@Param("startedAt") Timestamp timestamp, @Param("film") Film film);
}
