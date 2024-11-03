package com.assembly.vote.service.validator;

import com.assembly.vote.service.exception.NotFoundException;
import com.assembly.vote.service.service.VotingAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotingAgendaRequestValidator {

    private final VotingAgendaService votingAgendaService;

    public void validateVotingAgendaId(String votingAgendaId) {
        if (votingAgendaService.votingAgendaDoesNotExist(votingAgendaId)) {
            throw new NotFoundException("not_found_voting_agenda", votingAgendaId);
        }
    }
}
