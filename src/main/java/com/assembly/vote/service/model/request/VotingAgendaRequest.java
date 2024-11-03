package com.assembly.vote.service.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class VotingAgendaRequest {

    @NotBlank(message = "{title_must_not_be_empty}")
    private String title;

    @NotBlank(message = "{description_must_not_be_empty}")
    private String description;
}
