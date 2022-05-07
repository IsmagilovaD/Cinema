package ru.itis.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.cinema.models.Ticket;

public interface TicketsRepository extends JpaRepository<Ticket,Long> {
}
