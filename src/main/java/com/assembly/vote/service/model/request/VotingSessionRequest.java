package com.assembly.vote.service.model.request;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VotingSessionRequest {

    @NotNull(message = "{start_must_not_be_empty}")
    @JsonFormat(shape = STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime start;

    @JsonFormat(shape = STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime end;
}
