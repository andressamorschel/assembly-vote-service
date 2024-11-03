package com.assembly.vote.service.validator;

import static com.assembly.vote.service.exception.ErrorMessage.error;

import com.assembly.vote.service.exception.BadRequestException;
import com.assembly.vote.service.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteRequestValidator {

    private final VoteService voteService;

    public void validateVote(String memberId, String votingSessionId) {
        if (voteService.memberAlreadyVotedOnSession(votingSessionId, memberId)) {
            throw new BadRequestException(error("member_already_voted_in_session"));

        }
    }
}