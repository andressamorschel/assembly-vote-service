package com.assembly.vote.service.converter;

import com.assembly.vote.service.domain.Vote;
import com.assembly.vote.service.enumerated.VoteType;
import org.springframework.stereotype.Component;

@Component
public class VoteConverter {

    public Vote buildVote(String memberId, String votingSessionId, VoteType vote) {
        return Vote.builder()
                .votingSessionId(votingSessionId)
                .memberId(memberId)
                .vote(vote)
                .build();
    }
}
