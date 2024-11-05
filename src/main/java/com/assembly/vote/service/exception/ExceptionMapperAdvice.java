package com.assembly.vote.service.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionMapperAdvice {

    private final MessageSource messageSource;

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var fieldError = (FieldError) ex.getBindingResult().getAllErrors().get(0);
        var errorMessage = fieldError.getDefaultMessage();

        var errorResponse = new ErrorResponse(BAD_REQUEST.value(), errorMessage);

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(NotFoundException cause) {
        var errorMessage = getMessage(cause.getError());

        var errorResponse = new ErrorResponse(NOT_FOUND.value(), errorMessage);

        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(BadRequestException cause) {
        var errorMessage = getMessage(cause.getError());

        var errorResponse = new ErrorResponse(BAD_REQUEST.value(), errorMessage);

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handle(InternalServerErrorException cause) {
        var errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), cause.getMessage());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }


    private String getMessage(ErrorMessage error) {
        return messageSource.getMessage(error.getMessageKey(), error.getArguments().toArray(new String[0]), Locale.getDefault());
    }
}
