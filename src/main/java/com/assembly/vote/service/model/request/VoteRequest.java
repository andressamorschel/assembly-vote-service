package com.assembly.vote.service.model.request;

import com.assembly.vote.service.enumerated.VoteType;
import lombok.Getter;

@Getter
public class VoteRequest {

    private VoteType vote;
}
