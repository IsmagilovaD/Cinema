package ru.itis.cinema.services;

import ru.itis.cinema.dto.SignUpDto;

public interface SignUpService {
    void signUp(SignUpDto signUpDto);

    void confirm(String confirmCode);
}
