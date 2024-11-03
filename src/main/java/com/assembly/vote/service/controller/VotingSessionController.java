package com.assembly.vote.service.controller;

import com.assembly.vote.service.converter.VotingSessionConverter;
import com.assembly.vote.service.domain.VotingSession;
import com.assembly.vote.service.model.request.VotingSessionRequest;
import com.assembly.vote.service.repository.result.VoteCountResult;
import com.assembly.vote.service.service.VotingSessionService;
import com.assembly.vote.service.validator.VotingSessionRequestValidator;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/voting/sessions")
public class VotingSessionController {

    private final VotingSessionService votingSessionService;

    private final VotingSessionConverter votingSessionConverter;

    private final VotingSessionRequestValidator votingSessionRequestValidator;

    @PostMapping("/agenda/{votingAgendaId}/start")
    public VotingSession startVotingSession(@PathVariable String votingAgendaId, @RequestBody @Valid VotingSessionRequest votingSessionRequest) {
        votingSessionRequestValidator.validadeVotingSessionRequest(votingSessionRequest);

        var votingSession = votingSessionConverter.buildVotingSession(votingSessionRequest, votingAgendaId);

        var savedVotingSession = votingSessionService.saveVotingSession(votingSession);

        log.info("Successfully created voting session: {}", savedVotingSession);

        return savedVotingSession;
    }

    @GetMapping
    public List<VotingSession> listVotingSessions() {
        return votingSessionService.findVotingSessions();
    }

    @GetMapping("/{votingSessionId}/result")
    public List<VoteCountResult> getVoteSessionResult(@PathVariable String votingSessionId) {
        return votingSessionService.findVotingSessionResult(votingSessionId);
    }
}
