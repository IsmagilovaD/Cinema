package ru.itis.cinema.dto;

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
public class SessionDto {
    private Long hallNumber;
    private String filmName;
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
