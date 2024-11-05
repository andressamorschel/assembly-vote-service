package com.assembly.vote.service.repository.result;

public record VotingResult(
        String id,
        String title,
        String description,
        VoteResult result
) {}
