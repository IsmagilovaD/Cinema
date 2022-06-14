package ru.itis.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cinema.models.Session;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Сеанс")
public class SessionDto {

    @Schema(description = "Номер зала", example = "1")
    private Long hallNumber;
    @Schema(description = "Название фильма", example = "THE BATMAN")
    private String filmName;

    //TO DO
    @Schema(description = "Начало фильма", example = "...")
    private Timestamp startedAt;

    public static SessionDto from(Session session){
        return SessionDto.builder()
                .hallNumber(session.getHall().getId())
                .filmName(session.getFilm().getName())
                .startedAt(session.getStartedAt())
                .build();
    }

    public static List<SessionDto> from(List<Session> sessions){
        return sessions.stream().map(SessionDto::from).collect(Collectors.toList());
    }
}
