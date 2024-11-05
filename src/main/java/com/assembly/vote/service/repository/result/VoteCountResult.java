package com.assembly.vote.service.repository.result;

import com.assembly.vote.service.enumerated.VoteType;

public record VoteCountResult(
        VoteType vote,
        long count
) {}
