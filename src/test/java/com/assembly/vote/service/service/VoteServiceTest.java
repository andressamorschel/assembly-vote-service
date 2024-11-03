package com.assembly.vote.service.service;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.assembly.vote.service.domain.Vote;
import com.assembly.vote.service.repository.VoteQueryRepository;
import com.assembly.vote.service.repository.VoteRepository;
import com.assembly.vote.service.repository.result.VoteCountResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteQueryRepository voteQueryRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    void shouldGetResultSuccessfully() {
        var votingSessionId = "voting-session-id";
        var voteCountResult = mock(VoteCountResult.class);
        given(voteQueryRepository.getVoteSessionResult(votingSessionId)).willReturn(singletonList(voteCountResult));

        var result = voteService.getResult(votingSessionId);

        assertThat(result).contains(voteCountResult);
        verify(voteQueryRepository).getVoteSessionResult(votingSessionId);
    }

    @Test
    void shouldSaveVoteSuccessfully() {
        var vote = mock(Vote.class);
        given(voteRepository.save(vote)).willReturn(vote);

        var result = voteService.saveVote(vote);

        assertThat(result).isEqualTo(vote);
        verify(voteRepository).save(vote);
    }

    @Test
    void shouldMemberAlreadyVotedOnSessionReturnTrue() {
        var votingSessionId = "voting-session-id";
        var memberId = "member-id";
        given(voteRepository.memberHasAlreadyVotedInSession(memberId, votingSessionId)).willReturn(true);

        var result = voteService.memberAlreadyVotedOnSession(votingSessionId, memberId);

        assertThat(result).isTrue();
        verify(voteRepository).memberHasAlreadyVotedInSession(memberId, votingSessionId);
    }

    @Test
    void shouldMemberAlreadyVotedOnSessionReturnFalse() {
        var votingSessionId = "voting-session-id";
        var memberId = "member-id";

        var result = voteService.memberAlreadyVotedOnSession(votingSessionId, memberId);

        assertThat(result).isFalse();
        verify(voteRepository).memberHasAlreadyVotedInSession(memberId, votingSessionId);
    }

}
