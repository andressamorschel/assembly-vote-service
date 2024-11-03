package com.assembly.vote.service.service;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.assembly.vote.service.domain.VotingSession;
import com.assembly.vote.service.repository.VotingSessionRepository;
import com.assembly.vote.service.repository.result.VoteCountResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VotingSessionServiceTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private VoteService voteService;

    @InjectMocks
    private VotingSessionService votingSessionService;

    @Test
    void shouldSaveVotingSessionSuccessfully() {
        var votingSession = mock(VotingSession.class);
        given(votingSessionRepository.save(votingSession)).willReturn(votingSession);

        var result = votingSessionService.saveVotingSession(votingSession);

        assertThat(result).isEqualTo(votingSession);
        verify(votingSessionRepository).save(votingSession);
    }

    @Test
    void shouldFindVotingSessionsSuccessfully() {
        var votingSession = mock(VotingSession.class);
        given(votingSessionRepository.findAll()).willReturn(singletonList(votingSession));

        var result = votingSessionService.findVotingSessions();

        assertThat(result).contains(votingSession);
        verify(votingSessionRepository).findAll();
    }

    @Test
    void shouldFindVotingSessionResultSuccessfully() {
        var voteCountResult = mock(VoteCountResult.class);
        var votingSessionId = "voting-session-id";
        given(voteService.getResult(votingSessionId)).willReturn(singletonList(voteCountResult));

        var result = votingSessionService.findVotingSessionResult(votingSessionId);

        assertThat(result).contains(voteCountResult);
        verify(voteService).getResult(votingSessionId);
    }
}
