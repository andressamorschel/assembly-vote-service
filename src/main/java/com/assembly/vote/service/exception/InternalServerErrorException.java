package com.assembly.vote.service.exception;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message, Object... arguments) {
        super(arrayFormat(message, arguments).getMessage());
    }

}