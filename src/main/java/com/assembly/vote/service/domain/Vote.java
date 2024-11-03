package com.assembly.vote.service.domain;

import com.assembly.vote.service.enumerated.VoteType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Vote(
        @Id
        String id,
        String memberId,
        String votingAgenda,
        VoteType vote
) {}
