package com.assembly.vote.service.validator;

import static com.assembly.vote.service.exception.ErrorMessage.error;

import com.assembly.vote.service.exception.BadRequestException;
import com.assembly.vote.service.service.MemberService;
import com.assembly.vote.service.service.VotingAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteRequestValidator {

    private final VotingAgendaService votingAgendaService;

    private final MemberService memberService;

    public void validateVote(String memberId, String votingAgendaId) {
        if (memberService.cannotVote(memberId)) {
            throw new BadRequestException(error("not_allowed_to_vote"));
        }

        if (votingAgendaService.memberHasVoted(votingAgendaId, memberId)) {
            throw new BadRequestException(error("member_already_voted_in_session"));
        }
    }
}
