package com.assembly.vote.service.exception;

public record ErrorResponse(
        int status,
        String message
) {}
