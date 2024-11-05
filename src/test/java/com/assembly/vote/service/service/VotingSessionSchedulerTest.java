package com.assembly.vote.service.service;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.assembly.vote.service.repository.result.VotingResult;
import com.assembly.vote.service.service.scheduler.VotingSessionScheduler;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VotingSessionSchedulerTest {

    @Mock
    private SQSService sqsService;

    @Mock
    private VotingAgendaService votingAgendaService;

    @InjectMocks
    private VotingSessionScheduler votingSessionScheduler;

    @Captor
    private ArgumentCaptor<List<String>> agendaIdsCapor;

    @Test
    void shouldProcessExpiredSessionsSuccessfully() {
        var votingResult = mock(VotingResult.class);
        given(votingResult.id()).willReturn("agenda-id");
        given(votingAgendaService.findExpiredSessions()).willReturn(singletonList(votingResult));

        votingSessionScheduler.processExpiredSessions();

        verify(sqsService).sendMessage(votingResult);
        verify(votingAgendaService).findExpiredSessions();
        verify(votingAgendaService).markAsNotificationSent(agendaIdsCapor.capture());
        assertThat(agendaIdsCapor.getValue()).containsOnly("agenda-id");
    }
}
