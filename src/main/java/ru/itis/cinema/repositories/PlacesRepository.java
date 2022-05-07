package ru.itis.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Hall;
import ru.itis.cinema.models.Place;

import java.util.Optional;

public interface PlacesRepository extends JpaRepository<Place,Long> {
    Optional<Place> findPlaceByHallAndNumber(Hall hall, Number number);
}
