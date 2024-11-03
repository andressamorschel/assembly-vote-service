package com.assembly.vote.service.service;

import com.assembly.vote.service.domain.VotingSession;
import com.assembly.vote.service.repository.VotingSessionRepository;
import com.assembly.vote.service.repository.result.VoteCountResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotingSessionService {

    private final VotingSessionRepository votingSessionRepository;

    private final VoteService voteService;

    public VotingSession saveVotingSession(VotingSession votingSession) {
        return votingSessionRepository.save(votingSession);
    }

    public List<VotingSession> findVotingSessions() {
        return votingSessionRepository.findAll();
    }

    public List<VoteCountResult> findVotingSessionResult(String votingSessionId) {
        return voteService.getResult(votingSessionId);
    }
}
