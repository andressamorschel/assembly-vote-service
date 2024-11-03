package com.assembly.vote.service.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.assembly.vote.service.converter.VotingAgendaConverter;
import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.model.request.VotingAgendaRequest;
import com.assembly.vote.service.service.VotingAgendaService;
import com.assembly.vote.service.validator.VotingAgendaRequestValidator;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping
    @ResponseStatus(CREATED)
    public VotingAgenda createVotingAgenda(@RequestBody @Valid VotingAgendaRequest votingAgendaRequest) {
        var votingAgenda = votingAgendaConverter.buildVotingAgenda(votingAgendaRequest);

        var savedVotingAgenda = votingAgendaService.saveVotingAgenda(votingAgenda);

        log.info("Successfully created voting agenda: {}", savedVotingAgenda);

        return savedVotingAgenda;
    }

    @GetMapping
    public List<VotingAgenda> listVotingAgendas() {
        return votingAgendaService.listVotingAgendas();
    }

    @DeleteMapping("/{votingAgendaId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteVotingAgenda(@PathVariable String votingAgendaId) {
        log.info("Voting agenda deletion requested for: {}", votingAgendaId);
        votingAgendaRequestValidator.validateVotingAgendaId(votingAgendaId);
        votingAgendaService.deleteVotingAgenda(votingAgendaId);
    }
}
