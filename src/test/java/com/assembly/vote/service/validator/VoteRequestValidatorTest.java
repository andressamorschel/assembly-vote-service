package com.assembly.vote.service.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.assembly.vote.service.exception.BadRequestException;
import com.assembly.vote.service.service.MemberService;
import com.assembly.vote.service.service.VoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoteRequestValidatorTest {

    @Mock
    private MemberService memberService;

    @Mock
    private VoteService voteService;

    @InjectMocks
    private VoteRequestValidator voteRequestValidator;

    @Test
    void shouldValidateVoteSuccessfully() {
        var memberId = "member-id";
        var votingSessionId = "vote-session-id";

        assertThatCode(() -> voteRequestValidator.validateVote(memberId, votingSessionId))
                .doesNotThrowAnyException();

        verify(memberService).cannotVote(memberId);
        verify(voteService).memberAlreadyVotedOnSession(votingSessionId, memberId);
    }

    @Test
    void shouldValidateVoteThrowsBadRequestExceptionWhenMemberCannotVote() {
        var memberId = "member-id";
        var votingSessionId = "vote-session-id";
        given(memberService.cannotVote(memberId)).willReturn(true);

        assertThatCode(() -> voteRequestValidator.validateVote(memberId, votingSessionId))
                .isInstanceOf(BadRequestException.class);

        verify(memberService).cannotVote(memberId);
        verifyNoInteractions(voteService);
    }


    @Test
    void shouldValidateVoteThrowsBadRequestExceptionWhenMemberAlreadyVoted() {
        var memberId = "member-id";
        var votingSessionId = "vote-session-id";
        given(voteService.memberAlreadyVotedOnSession(votingSessionId, memberId)).willReturn(true);

        assertThatCode(() -> voteRequestValidator.validateVote(memberId, votingSessionId))
                .isInstanceOf(BadRequestException.class);

        verify(memberService).cannotVote(memberId);
        verify(voteService).memberAlreadyVotedOnSession(votingSessionId, memberId);
    }
}
