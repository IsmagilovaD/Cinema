package ru.itis.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Customer;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
}
