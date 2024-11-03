package com.assembly.vote.service.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record VotingAgenda(
        @Id
        String id,
        String title,
        String description,
        LocalDateTime start,
        LocalDateTime end
) {}
