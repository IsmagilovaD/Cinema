package ru.itis.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cinema.validation.annotations.Password;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
    @NotBlank(message = "Name must not be empty")
    private String firstName;
    @NotBlank(message = "Name must not be empty")
    private String lastName;
    @NotBlank(message = "Email must not be empty")
    private String email;
    @Password
    private String password;
}
