package com.assembly.vote.service.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.assembly.vote.service.converter.VoteConverter;
import com.assembly.vote.service.domain.Vote;
import com.assembly.vote.service.model.request.VoteRequest;
import com.assembly.vote.service.service.VoteService;
import com.assembly.vote.service.validator.VoteRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/voting/sessions/vote")
public class VoteController {

    private final VoteService voteService;

    private final VoteConverter voteConverter;

    private final VoteRequestValidator voteRequestValidator;

    @PostMapping("/{votingSession}/member/{memberId}")
    @ResponseStatus(CREATED)
    public Vote vote(@PathVariable String votingSession, @PathVariable String memberId, @RequestBody @Valid VoteRequest voteRequest) {
        voteRequestValidator.validateVote(memberId, votingSession);

        var vote = voteConverter.buildVote(memberId, votingSession, voteRequest.getVote());

        var savedVote = voteService.saveVote(vote);

        log.info("Successfully created vote: {}", savedVote);

        return savedVote;
    }
}
