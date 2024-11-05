package com.assembly.vote.service.model.response;

import com.assembly.vote.service.domain.VotingSession;
import lombok.Builder;

@Builder
public record VotingAgendaResponse(
        String id,
        String title,
        String description,
        VotingSession votingSession
) {}