package com.assembly.vote.service.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.assembly.vote.service.converter.VoteConverter;
import com.assembly.vote.service.converter.VotingAgendaConverter;
import com.assembly.vote.service.converter.VotingSessionConverter;
import com.assembly.vote.service.model.request.VoteRequest;
import com.assembly.vote.service.model.request.VotingAgendaRequest;
import com.assembly.vote.service.model.request.VotingSessionRequest;
import com.assembly.vote.service.model.response.VotingAgendaResponse;
import com.assembly.vote.service.repository.result.VoteCountResult;
import com.assembly.vote.service.service.VotingAgendaService;
import com.assembly.vote.service.validator.VoteRequestValidator;
import com.assembly.vote.service.validator.VotingAgendaRequestValidator;
import com.assembly.vote.service.validator.VotingSessionRequestValidator;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/voting/agendas")
public class VotingAgendaController {

    private final VotingAgendaService votingAgendaService;

    private final VotingAgendaConverter votingAgendaConverter;

    private final VotingAgendaRequestValidator votingAgendaRequestValidator;

    private final VotingSessionConverter votingSessionConverter;

    private final VotingSessionRequestValidator votingSessionRequestValidator;

    private final VoteConverter voteConverter;

    private final VoteRequestValidator voteRequestValidator;

    @PostMapping
    @ResponseStatus(CREATED)
    public VotingAgendaResponse createVotingAgenda(@RequestBody @Valid VotingAgendaRequest votingAgendaRequest) {
        log.info("Creating Voting Agenda: {}", votingAgendaRequest);

        var votingAgenda = votingAgendaConverter.buildVotingAgenda(votingAgendaRequest);

        var savedVotingAgenda = votingAgendaService.saveVotingAgenda(votingAgenda);

        return votingAgendaConverter.buildVotingAgendaResponse(savedVotingAgenda);
    }

    @GetMapping
    public List<VotingAgendaResponse> listVotingAgendas() {
        var votingAgendas = votingAgendaService.listVotingAgendas();
        return votingAgendaConverter.buildVotingAgendaResponse(votingAgendas);
    }

    @DeleteMapping("/{votingAgendaId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteVotingAgenda(@PathVariable String votingAgendaId) {
        log.info("Voting agenda deletion requested for: {}", votingAgendaId);
        votingAgendaRequestValidator.validateVotingAgendaId(votingAgendaId);
        votingAgendaService.deleteVotingAgenda(votingAgendaId);
    }

    @PatchMapping("/{votingAgendaId}/start/session")
    public VotingAgendaResponse startVotingSession(@PathVariable String votingAgendaId, @RequestBody @Valid VotingSessionRequest votingSessionRequest) {
        votingSessionRequestValidator.validadeVotingSessionRequest(votingSessionRequest);

        log.info("Creating Voting Session to voting agenda: {}", votingAgendaId);

        var votingSession = votingSessionConverter.buildVotingSession(votingSessionRequest);

        var votingAgenda = votingAgendaService.findVotingAgendaById(votingAgendaId);

        var votingAgendaWithSession = votingAgendaConverter.buildVotingAgendaAndSession(votingSession, votingAgenda);

        var updatedVotingAgenda = votingAgendaService.saveVotingAgenda(votingAgendaWithSession);

        return votingAgendaConverter.buildVotingAgendaResponse(updatedVotingAgenda);
    }

    @PostMapping("/{votingAgendaId}/member/{memberId}")
    @ResponseStatus(CREATED)
    public VotingAgendaResponse vote(@PathVariable String votingAgendaId, @PathVariable String memberId, @RequestBody @Valid VoteRequest voteRequest) {
        voteRequestValidator.validateVote(memberId, votingAgendaId);

        log.info("Creating vote to voting agenda: {}, member: {}", votingAgendaId, memberId);

        var vote = voteConverter.buildVote(memberId, voteRequest.getVote());

        var votingAgenda = votingAgendaService.findVotingAgendaById(votingAgendaId);

        var votingAgendaWithVote = votingAgendaConverter.buildVotingAgendaAndAddVote(vote, votingAgenda);

        var updatedVotingAgenda = votingAgendaService.saveVotingAgenda(votingAgendaWithVote);

        return votingAgendaConverter.buildVotingAgendaResponse(updatedVotingAgenda);
    }

    @GetMapping("/{votingAgendaId}/result")
    public List<VoteCountResult> getVoteSessionResult(@PathVariable String votingAgendaId) {
        return votingAgendaService.getVoteCountResults(votingAgendaId);
    }
}
