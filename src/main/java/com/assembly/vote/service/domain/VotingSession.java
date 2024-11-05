package com.assembly.vote.service.domain;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record VotingSession(
        LocalDateTime start,
        LocalDateTime end,
        boolean notificationSent
) {}