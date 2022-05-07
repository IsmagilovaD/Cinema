package ru.itis.cinema.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Customer;
import ru.itis.cinema.models.Film;
import ru.itis.cinema.models.Review;

public interface ReviewsRepository extends JpaRepository<Review, Long> {
    Page<Review> findReviewByFilm(Pageable pageable, Film film);
    Page<Review> findReviewByCustomer(Pageable pageable, Customer customer);
}
