package com.assembly.vote.service.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.assembly.vote.service.exception.BadRequestException;
import com.assembly.vote.service.service.MemberService;
import com.assembly.vote.service.service.VotingAgendaService;
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
    private VotingAgendaService votingAgendaService;

    @InjectMocks
    private VoteRequestValidator voteRequestValidator;

    @Test
    void shouldValidateVoteSuccessfully() {
        var memberId = "member-id";
        var votingAgendaId = "vote-agenda-id";

        assertThatCode(() -> voteRequestValidator.validateVote(memberId, votingAgendaId))
                .doesNotThrowAnyException();

        verify(memberService).cannotVote(memberId);
        verify(votingAgendaService).memberHasVoted(votingAgendaId, memberId);
    }

    @Test
    void shouldValidateVoteThrowsBadRequestExceptionWhenMemberCannotVote() {
        var memberId = "member-id";
        var votingAgendaId = "vote-agenda-id";
        given(memberService.cannotVote(memberId)).willReturn(true);

        assertThatCode(() -> voteRequestValidator.validateVote(memberId, votingAgendaId))
                .isInstanceOf(BadRequestException.class);

        verify(memberService).cannotVote(memberId);
        verifyNoInteractions(votingAgendaService);
    }


    @Test
    void shouldValidateVoteThrowsBadRequestExceptionWhenMemberAlreadyVoted() {
        var memberId = "member-id";
        var votingAgendaId = "vote-agenda-id";
        given(votingAgendaService.memberHasVoted(votingAgendaId, memberId)).willReturn(true);

        assertThatCode(() -> voteRequestValidator.validateVote(memberId, votingAgendaId))
                .isInstanceOf(BadRequestException.class);

        verify(memberService).cannotVote(memberId);
        verify(votingAgendaService).memberHasVoted(votingAgendaId, memberId);
    }
}
