package com.assembly.vote.service.exception;

import static com.assembly.vote.service.exception.ErrorMessage.error;
import static java.lang.String.format;

import java.util.Optional;

public class NotFoundException extends RuntimeException {

    private final String resourceNameKey;
    private final String resourceId;

    public NotFoundException(String resourceNameKey, String resourceId) {
        super(format("%s %s not found", resourceNameKey, resourceId));
        this.resourceNameKey = resourceNameKey;
        this.resourceId = resourceId;
    }

    public ErrorMessage getError() {
        return Optional.ofNullable(resourceId)
                .map(id -> error(resourceNameKey, id))
                .orElseGet(() -> error(resourceNameKey));
    }

}
