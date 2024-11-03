package com.assembly.vote.service.validator;

import static com.assembly.vote.service.exception.ErrorMessage.error;
import static java.time.LocalDateTime.now;

import com.assembly.vote.service.exception.BadRequestException;
import com.assembly.vote.service.model.request.VotingSessionRequest;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class VotingSessionRequestValidator {

    public void validadeVotingSessionRequest(VotingSessionRequest votingSessionRequest) {
        if (startIsBeforeNow(votingSessionRequest.getStart())) {
            throw new BadRequestException(error("start_must_not_be_in_the_past"));
        }

        if (endIsBeforeStart(votingSessionRequest.getEnd(), votingSessionRequest.getStart())) {
            throw new BadRequestException(error("end_must_be_after_start"));
        }
    }

    private boolean startIsBeforeNow(LocalDateTime start) {
        return start.isBefore(now());
    }

    private boolean endIsBeforeStart(LocalDateTime end, LocalDateTime start) {
        return end != null && end.isBefore(start);
    }
}
