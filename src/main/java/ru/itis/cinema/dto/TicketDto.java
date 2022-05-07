package ru.itis.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cinema.models.Place;
import ru.itis.cinema.models.Ticket;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {
    private String film;
    private Timestamp startedAt;
    private Byte placeNumber;
    private Long hallNumber;

    public static TicketDto from(Ticket ticket){
        return TicketDto.builder()
                .film(ticket.getSession().getFilm().getName())
                .hallNumber(ticket.getSession().getHall().getId())
                .placeNumber(ticket.getPlace().getNumber())
                .startedAt(ticket.getSession().getStartedAt())
                .build();
    }
}
