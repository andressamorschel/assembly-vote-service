package com.assembly.vote.service.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final ErrorMessage error;

    public BadRequestException(ErrorMessage error) {
        super("Bad request");
        this.error = error;
    }
}