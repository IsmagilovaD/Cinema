package ru.itis.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> getCustomerByConfirmCode(String confirmCode);
}
