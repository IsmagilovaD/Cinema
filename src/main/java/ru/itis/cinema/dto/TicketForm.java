package ru.itis.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketForm {
    @NotNull(message = "Session must not be empty")
    private Long sessionId;
    @NotNull(message = "Places must not be empty")
    private Byte placeNumber;
}
