package ru.itis.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Форма для покупки билета")
public class TicketForm {

    @Schema(description = "ID сессии", example = "1")
    @NotNull(message = "Session must not be empty")
    private Long sessionId;

    @Schema(description = "Номер места", example = "1")
    @NotNull(message = "Places must not be empty")
    private Byte placeNumber;
}
