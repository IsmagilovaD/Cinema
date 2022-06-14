package ru.itis.cinema.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Получение билета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о билете",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = TicketDto.class)
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<TicketDto> getTicket(
            @Parameter(description = "Информация о билете") @RequestBody TicketForm ticketForm,
            Principal principal){
        String customer = principal.getName();
        return ResponseEntity.ok(ticketService.getTicket(ticketForm, customer));
    }

}
