package ru.itis.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cinema.models.Ticket;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Schema(description = "Билет")
public class TicketDto {

    @Schema(description = "Название фильма", example = "THE BATMAN")
    private String filmName;
    //TO DO
    @Schema(description = "Начало фильма", example = "...")
    private Timestamp startedAt;
    @Schema(description = "Номер места", example = "1")
    private Byte placeNumber;
    @Schema(description = "Номер зала", example = "1")
    private Long hallNumber;

    public static TicketDto from(Ticket ticket){
        return TicketDto.builder()
                .filmName(ticket.getSession().getFilm().getName())
                .hallNumber(ticket.getSession().getHall().getId())
                .placeNumber(ticket.getPlace().getNumber())
                .startedAt(ticket.getSession().getStartedAt())
                .build();
    }
}
