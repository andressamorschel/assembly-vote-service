package com.assembly.vote.service.model.response;

import com.assembly.vote.service.repository.result.VoteCountResult;
import java.time.LocalDateTime;

public record FinsihedVotingSessionResponse(
        String id,
        String votingAgendaId,
        LocalDateTime start,
        LocalDateTime end,
        VoteCountResult result
) {}
