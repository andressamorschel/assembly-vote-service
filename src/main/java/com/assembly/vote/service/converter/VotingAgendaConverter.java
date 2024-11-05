package com.assembly.vote.service.converter;

import static java.util.stream.Stream.concat;

import com.assembly.vote.service.domain.Vote;
import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.domain.VotingSession;
import com.assembly.vote.service.model.request.VotingAgendaRequest;
import com.assembly.vote.service.model.response.VotingAgendaResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class VotingAgendaConverter {

    public List<VotingAgendaResponse> buildVotingAgendaResponse(List<VotingAgenda> votingAgendas) {
        return votingAgendas.stream()
                .map(this::buildVotingAgendaResponse)
                .toList();
    }

    public VotingAgendaResponse buildVotingAgendaResponse(VotingAgenda votingAgenda) {
        return VotingAgendaResponse.builder()
                .id(votingAgenda.id())
                .description(votingAgenda.description())
                .title(votingAgenda.title())
                .votingSession(votingAgenda.votingSession())
                .build();
    }

    public VotingAgenda buildVotingAgenda(VotingAgendaRequest votingAgendaRequest) {
        return VotingAgenda.builder()
                .description(votingAgendaRequest.getDescription())
                .title(votingAgendaRequest.getTitle())
                .build();
    }

    public VotingAgenda buildVotingAgendaAndSession(VotingSession votingSession, VotingAgenda votingAgenda) {
        return VotingAgenda.builder()
                .id(votingAgenda.id())
                .description(votingAgenda.description())
                .title(votingAgenda.title())
                .votingSession(votingSession)
                .build();
    }

    public VotingAgenda buildVotingAgendaAndAddVote(Vote vote, VotingAgenda votingAgenda) {
        var votes = getVotes(votingAgenda.votes(), vote);
        return VotingAgenda.builder()
                .id(votingAgenda.id())
                .description(votingAgenda.description())
                .title(votingAgenda.title())
                .votingSession(votingAgenda.votingSession())
                .votes(votes)
                .build();
    }

    private List<Vote> getVotes(List<Vote> votes, Vote toAdd) {
        return concat(getVotesOrEmptyList(votes).stream(), Stream.of(toAdd)).toList();
    }

    private List<Vote> getVotesOrEmptyList(List<Vote> votes) {
        return Optional.ofNullable(votes)
                .orElseGet(Collections::emptyList);
    }

}
