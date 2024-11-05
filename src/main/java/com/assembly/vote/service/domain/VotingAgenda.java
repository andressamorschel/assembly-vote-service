package com.assembly.vote.service.domain;

import java.util.List;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
public record VotingAgenda(
        @Id
        String id,
        String title,
        String description,
        VotingSession votingSession,
        List<Vote> votes
) {}
