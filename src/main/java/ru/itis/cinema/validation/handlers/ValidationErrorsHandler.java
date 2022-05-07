package ru.itis.cinema.validation.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.cinema.validation.dto.ValidationErrorDto;
import ru.itis.cinema.validation.dto.ValidationExceptionResponse;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationErrorsHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationExceptionResponse> handleException(MethodArgumentNotValidException exception) {
        List<ValidationErrorDto> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {

            String errorMessage = error.getDefaultMessage();
            ValidationErrorDto errorDto = ValidationErrorDto.builder()
                    .message(errorMessage)
                    .build();

            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                errorDto.setField(fieldName);
            } else if (error instanceof ObjectError) {
                String objectName = error.getObjectName();
                errorDto.setObject(objectName);
            }
            errors.add(errorDto);
        });

        return new ResponseEntity<>(ValidationExceptionResponse.builder()
                .errors(errors)
                .build(),
                HttpStatus.BAD_REQUEST);
    }


}
