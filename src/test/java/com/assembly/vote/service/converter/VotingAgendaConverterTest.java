package com.assembly.vote.service.converter;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.assembly.vote.service.domain.Vote;
import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.domain.VotingSession;
import com.assembly.vote.service.model.request.VotingAgendaRequest;
import java.util.List;
import org.junit.jupiter.api.Test;

class VotingAgendaConverterTest {

    private final VotingAgendaConverter votingAgendaConverter = new VotingAgendaConverter();

    @Test
    void shouldBuildVotingAgendaSuccessfully() {
        var votingAgendaRequest = mock(VotingAgendaRequest.class);
        given(votingAgendaRequest.getDescription()).willReturn("agenda description");
        given(votingAgendaRequest.getTitle()).willReturn("agenda title");

        var result = votingAgendaConverter.buildVotingAgenda(votingAgendaRequest);

        assertThat(result.description()).isEqualTo("agenda description");
        assertThat(result.title()).isEqualTo("agenda title");
    }

    @Test
    void shouldBuildVotingAgendaAndSessionSuccessfully() {
        var votingAgenda = mock(VotingAgenda.class);
        var votingSession = mock(VotingSession.class);
        given(votingAgenda.description()).willReturn("agenda description");
        given(votingAgenda.title()).willReturn("agenda title");

        var result = votingAgendaConverter.buildVotingAgendaAndSession(votingSession, votingAgenda);

        assertThat(result.description()).isEqualTo("agenda description");
        assertThat(result.title()).isEqualTo("agenda title");
        assertThat(result.votingSession()).isEqualTo(votingSession);
    }

    @Test
    void shouldBuildVotingAgendaAndAddVoteSuccessfully() {
        var votingAgenda = mock(VotingAgenda.class);
        var firstVote = mock(Vote.class);
        var secondVote = mock(Vote.class);
        var votingSession = mock(VotingSession.class);
        given(votingAgenda.description()).willReturn("agenda description");
        given(votingAgenda.title()).willReturn("agenda title");
        given(votingAgenda.votingSession()).willReturn(votingSession);
        given(votingAgenda.votes()).willReturn(List.of(firstVote));

        var result = votingAgendaConverter.buildVotingAgendaAndAddVote(secondVote, votingAgenda);

        assertThat(result.description()).isEqualTo("agenda description");
        assertThat(result.title()).isEqualTo("agenda title");
        assertThat(result.votingSession()).isEqualTo(votingSession);
        assertThat(result.votes()).contains(firstVote, secondVote);
    }
}
