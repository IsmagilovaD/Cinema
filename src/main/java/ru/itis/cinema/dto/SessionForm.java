package ru.itis.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Форма для изменения сеанса")
public class SessionForm {

    @Schema(description = "Название фильма", example = "THE BATMAN")
    private String filmName;
    //TO DO
    @Schema(description = "Начало фильма", example = "...")
    private Timestamp startedAt;
    @Schema(description = "Номер зала", example = "1")
    private Long hallNumber;
}
