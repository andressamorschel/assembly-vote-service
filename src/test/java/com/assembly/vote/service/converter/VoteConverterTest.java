package com.assembly.vote.service.converter;

import static com.assembly.vote.service.enumerated.VoteType.NO;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.Test;

class VoteConverterTest {

    private final VoteConverter voteConverter = new VoteConverter();

    @Test
    void shouldBuildVoteSuccessfully() {
        var memberId = "member-id";

        var result = voteConverter.buildVote(memberId, NO);

        assertThat(result.memberId()).isEqualTo(memberId);
        assertThat(result.vote()).isEqualTo(NO);
    }
}
