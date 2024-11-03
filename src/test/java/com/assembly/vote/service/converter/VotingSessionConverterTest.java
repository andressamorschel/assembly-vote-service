package com.assembly.vote.service.converter;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.assembly.vote.service.model.request.VotingSessionRequest;
import org.junit.jupiter.api.Test;

class VotingSessionConverterTest {

    private final VotingSessionConverter votingSessionConverter = new VotingSessionConverter();

    @Test
    void shouldBuildVotingSessionSuccessfully() {
        var votingAgendaId = "voting-agenda-id";
        var tomorrow = now().plusDays(1);
        var start = now();
        var votingSessionRequest = mock(VotingSessionRequest.class);
        given(votingSessionRequest.getStart()).willReturn(start);
        given(votingSessionRequest.getEnd()).willReturn(tomorrow);

        var result = votingSessionConverter.buildVotingSession(votingSessionRequest, votingAgendaId);

        assertThat(result.votingAgendaId()).isEqualTo(votingAgendaId);
        assertThat(result.end()).isEqualTo(tomorrow);
        assertThat(result.start()).isEqualTo(start);
    }

    @Test
    void shouldBuildVotingSessionSuccessfullyWhenEndIsNull() {
        var votingAgendaId = "voting-agenda-id";
        var votingEnd = now().plusMinutes(1);
        var start = now();
        var votingSessionRequest = mock(VotingSessionRequest.class);
        given(votingSessionRequest.getStart()).willReturn(start);

        var result = votingSessionConverter.buildVotingSession(votingSessionRequest, votingAgendaId);

        assertThat(result.votingAgendaId()).isEqualTo(votingAgendaId);
        assertThat(result.end()).isEqualToIgnoringNanos(votingEnd);
        assertThat(result.start()).isEqualTo(start);
    }
}
