package com.assembly.vote.service.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
public record VotingSession(
        @Id
        String id,
        String votingAgendaId,
        LocalDateTime start,
        LocalDateTime end
) {}