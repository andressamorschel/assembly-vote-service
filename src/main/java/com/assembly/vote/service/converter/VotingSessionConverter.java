package com.assembly.vote.service.converter;

import com.assembly.vote.service.domain.VotingSession;
import com.assembly.vote.service.model.request.VotingSessionRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class VotingSessionConverter {

    private static final long DEFAULT_VOTING_AGENDA_DURATION = 1;

    public VotingSession buildVotingSession(VotingSessionRequest votingSessionRequest, String votingAgendaId) {
        return VotingSession.builder()
                .votingAgendaId(votingAgendaId)
                .start(votingSessionRequest.getStart())
                .end(getVotingEnd(votingSessionRequest))
                .build();
    }

    private LocalDateTime getVotingEnd(VotingSessionRequest votingSessionRequest) {
        return Optional.ofNullable(votingSessionRequest.getEnd())
                .orElseGet(() -> plusMinutes(votingSessionRequest.getStart()));
    }

    private LocalDateTime plusMinutes(LocalDateTime dateTime) {
        return dateTime.plusMinutes(DEFAULT_VOTING_AGENDA_DURATION);
    }
}
