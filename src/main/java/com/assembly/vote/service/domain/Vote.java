package com.assembly.vote.service.domain;

import com.assembly.vote.service.enumerated.VoteType;
import lombok.Builder;

@Builder
public record Vote(
        String memberId,
        VoteType vote
) {}
