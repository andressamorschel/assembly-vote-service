package com.assembly.vote.service.validator;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.assembly.vote.service.exception.BadRequestException;
import com.assembly.vote.service.model.request.VotingSessionRequest;
import org.junit.jupiter.api.Test;

class VotingSessionRequestValidatorTest {

    private final VotingSessionRequestValidator votingSessionRequestValidator = new VotingSessionRequestValidator();

    @Test
    void shouldValidadeVotingSessionRequestThrowsBadRequestExceptionWhenStartIsInThePast() {
        var votingSessionRequest = mock(VotingSessionRequest.class);
        given(votingSessionRequest.getStart()).willReturn(now().minusDays(1));

        assertThatCode(() -> votingSessionRequestValidator.validadeVotingSessionRequest(votingSessionRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void shouldValidadeVotingSessionRequestThrowsBadRequestExceptionWhenEndIsBeforeStart() {
        var votingSessionRequest = mock(VotingSessionRequest.class);
        given(votingSessionRequest.getStart()).willReturn(now().plusDays(1));
        given(votingSessionRequest.getStart()).willReturn(now());

        assertThatCode(() -> votingSessionRequestValidator.validadeVotingSessionRequest(votingSessionRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void shouldValidadeVotingSessionRequestSuccessfully() {
        var votingSessionRequest = mock(VotingSessionRequest.class);
        given(votingSessionRequest.getStart()).willReturn(now());
        given(votingSessionRequest.getStart()).willReturn(now().plusDays(1));

        assertThatCode(() -> votingSessionRequestValidator.validadeVotingSessionRequest(votingSessionRequest))
                .doesNotThrowAnyException();
    }
}
