package com.assembly.vote.service.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.assembly.vote.service.exception.NotFoundException;
import com.assembly.vote.service.service.VotingAgendaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VotingAgendaRequestValidatorTest {

    @Mock
    private VotingAgendaService votingAgendaService;

    @InjectMocks
    private VotingAgendaRequestValidator votingAgendaRequestValidator;

    @Test
    void shouldValidateVotingAgendaIdThrowsNotFoundException() {
        var votingAgendaId = "voting-agenda-id";
        given(votingAgendaService.votingAgendaDoesNotExist(votingAgendaId)).willReturn(true);

        assertThatCode(() -> votingAgendaRequestValidator.validateVotingAgendaId(votingAgendaId))
                .isInstanceOf(NotFoundException.class);

        verify(votingAgendaService).votingAgendaDoesNotExist(votingAgendaId);
    }

    @Test
    void shouldValidateVotingAgendaIdTSuccessfully() {
        var votingAgendaId = "voting-agenda-id";

        assertThatCode(() -> votingAgendaRequestValidator.validateVotingAgendaId(votingAgendaId))
                .doesNotThrowAnyException();

        verify(votingAgendaService).votingAgendaDoesNotExist(votingAgendaId);
    }
}
