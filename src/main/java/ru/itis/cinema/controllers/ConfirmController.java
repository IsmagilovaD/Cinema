package ru.itis.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.cinema.services.SignUpService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/confirm")
public class ConfirmController {
    private final SignUpService signUpService;

    @GetMapping("/{confirm-code}")
    public ResponseEntity<?> Confirm(@PathVariable("confirm-code") String confirmCode) {
        signUpService.confirm(confirmCode);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
