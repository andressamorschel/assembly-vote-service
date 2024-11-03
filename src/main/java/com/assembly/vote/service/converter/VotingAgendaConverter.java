package com.assembly.vote.service.converter;

import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.model.request.VotingAgendaRequest;
import org.springframework.stereotype.Component;

@Component
public class VotingAgendaConverter {

    public VotingAgenda buildVotingAgenda(VotingAgendaRequest votingAgendaRequest) {
        return VotingAgenda.builder()
                .description(votingAgendaRequest.getDescription())
                .title(votingAgendaRequest.getTitle())
                .build();
    }

}
