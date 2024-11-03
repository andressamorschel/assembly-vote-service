package com.assembly.vote.service.service;

import com.assembly.vote.service.domain.Vote;
import com.assembly.vote.service.repository.VoteQueryRepository;
import com.assembly.vote.service.repository.VoteRepository;
import com.assembly.vote.service.repository.result.VoteCountResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final VoteQueryRepository voteQueryRepository;

    public List<VoteCountResult> getResult(String votingSessionId) {
        return voteQueryRepository.getVoteSessionResult(votingSessionId);
    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public boolean memberAlreadyVotedOnSession(String votingSession, String memberId) {
        return voteRepository.memberHasAlreadyVotedInSession(memberId, votingSession);
    }
}
