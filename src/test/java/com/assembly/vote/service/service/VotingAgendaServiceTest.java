package com.assembly.vote.service.service;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.repository.VotingAgendaQueryRepository;
import com.assembly.vote.service.repository.VotingAgendaRepository;
import com.assembly.vote.service.repository.result.VotingResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VotingAgendaServiceTest {

    @Mock
    private VotingAgendaRepository votingAgendaRepository;

    @Mock
    private VotingAgendaQueryRepository votingAgendaQueryRepository;

    @InjectMocks
    private VotingAgendaService votingAgendaService;

    @Test
    void shouldMarkAsNotificationSentSuccessfully() {
        var ids = singletonList("agenda-id");

        votingAgendaService.markAsNotificationSent(ids);

        verify(votingAgendaQueryRepository).markVotingAgendasAsNotified(ids);
    }

    @Test
    void shouldFindExpiredSessionsSuccessfully() {
        var votingResult = mock(VotingResult.class);
        given(votingAgendaQueryRepository.getExpiredSessionsResult()).willReturn(singletonList(votingResult));

        var result = votingAgendaService.findExpiredSessions();

        assertThat(result).isNotEmpty();
        verify(votingAgendaQueryRepository).getExpiredSessionsResult();
    }

    @Test
    void shouldSaveVotingAgendaSuccessfully() {
        var votingAgenda = mock(VotingAgenda.class);
        given(votingAgendaRepository.save(votingAgenda)).willReturn(votingAgenda);

        var result = votingAgendaService.saveVotingAgenda(votingAgenda);

        assertThat(result).isEqualTo(votingAgenda);
        verify(votingAgendaRepository).save(votingAgenda);
    }

    @Test
    void shouldListVotingAgendasSuccessfully() {
        var votingAgenda = mock(VotingAgenda.class);
        given(votingAgendaRepository.findAll()).willReturn(singletonList(votingAgenda));

        var result = votingAgendaService.listVotingAgendas();

        assertThat(result).contains(votingAgenda);
        verify(votingAgendaRepository).findAll();
    }

    @Test
    void shouldDeleteVotingAgendaSuccessfully() {
        var votingAgendaId = "voting-agenda-id";

        votingAgendaService.deleteVotingAgenda(votingAgendaId);

        verify(votingAgendaRepository).deleteById(votingAgendaId);
    }

    @Test
    void shouldVotingAgendaDoesNotExistReturnFalse() {
        var votingAgendaId = "voting-agenda-id";
        given(votingAgendaRepository.existsById(votingAgendaId)).willReturn(true);

        var result = votingAgendaService.votingAgendaDoesNotExist(votingAgendaId);

        assertThat(result).isFalse();
        verify(votingAgendaRepository).existsById(votingAgendaId);
    }

    @Test
    void shouldVotingAgendaDoesNotExistReturnTrue() {
        var votingAgendaId = "voting-agenda-id";

        var result = votingAgendaService.votingAgendaDoesNotExist(votingAgendaId);

        assertThat(result).isTrue();
        verify(votingAgendaRepository).existsById(votingAgendaId);
    }
}
