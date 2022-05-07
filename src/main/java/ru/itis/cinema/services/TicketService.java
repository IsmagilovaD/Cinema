package ru.itis.cinema.services;

import ru.itis.cinema.dto.TicketDto;
import ru.itis.cinema.dto.TicketForm;

public interface TicketService {
    TicketDto getTicket(TicketForm ticketForm, String customer);
}
