package ru.itis.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.cinema.dto.TicketDto;
import ru.itis.cinema.dto.TicketForm;
import ru.itis.cinema.services.TicketService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketsController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDto> getTicket(@RequestBody TicketForm ticketForm, Principal principal){
        String customer = principal.getName();
        return ResponseEntity.ok(ticketService.getTicket(ticketForm, customer));
    }

}
