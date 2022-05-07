package ru.itis.cinema.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.cinema.dto.TicketDto;
import ru.itis.cinema.dto.TicketForm;
import ru.itis.cinema.exceptions.PlaceIsOccupiedException;
import ru.itis.cinema.models.Place;
import ru.itis.cinema.models.Session;
import ru.itis.cinema.models.Ticket;
import ru.itis.cinema.repositories.CustomerRepository;
import ru.itis.cinema.repositories.PlacesRepository;
import ru.itis.cinema.repositories.SessionRepository;
import ru.itis.cinema.repositories.TicketsRepository;
import ru.itis.cinema.services.TicketService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.itis.cinema.dto.TicketDto.from;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {
    private final SessionRepository sessionRepository;

    private final PlacesRepository placesRepository;

    private final TicketsRepository ticketsRepository;

    private final CustomerRepository customerRepository;
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public TicketDto getTicket(TicketForm ticketForm, String customerJWT) {
        Session session = sessionRepository.findById(ticketForm.getSessionId()).orElseThrow();
        Place place = placesRepository.findPlaceByHallAndNumber(session.getHall(), ticketForm.getPlaceNumber())
                .orElseThrow();
        if (place.getState().equals(Place.State.OCCUPIED)) throw new PlaceIsOccupiedException();
        else {
            place.setState(Place.State.OCCUPIED);
            placesRepository.save(place);
        }

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(customerJWT);
        String email = decodedJWT.getClaim("email").asString();
        Ticket ticket = Ticket.builder()
                .customer(customerRepository.findByEmail(email).orElseThrow())
                .session(session)
                .place(place)
                .build();
        return from(ticketsRepository.save(ticket));
    }
}
