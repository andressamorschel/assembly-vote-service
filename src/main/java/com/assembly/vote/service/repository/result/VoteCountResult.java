package com.assembly.vote.service.repository.result;

import com.assembly.vote.service.enumerated.VoteType;
import lombok.Getter;

@Getter
public class VoteCountResult {

    private VoteType vote;

    private long count;
}
