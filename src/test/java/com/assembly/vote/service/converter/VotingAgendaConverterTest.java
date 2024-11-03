package com.assembly.vote.service.converter;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.assembly.vote.service.model.request.VotingAgendaRequest;
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
}
