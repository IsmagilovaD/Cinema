package ru.itis.cinema.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.cinema.dto.SignUpDto;
import ru.itis.cinema.services.SignUpService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/signUp")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto){
        signUpService.signUp(signUpDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
