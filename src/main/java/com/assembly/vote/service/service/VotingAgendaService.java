package com.assembly.vote.service.service;

import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.exception.NotFoundException;
import com.assembly.vote.service.repository.VotingAgendaQueryRepository;
import com.assembly.vote.service.repository.VotingAgendaRepository;
import com.assembly.vote.service.repository.result.VoteCountResult;
import com.assembly.vote.service.repository.result.VotingResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotingAgendaService {

    private final VotingAgendaRepository votingAgendaRepository;

    private final VotingAgendaQueryRepository votingAgendaQueryRepository;

    public void markAsNotificationSent(List<String> votingAgendaIds) {
        votingAgendaQueryRepository.markVotingAgendasAsNotified(votingAgendaIds);
    }

    public List<VotingResult> findExpiredSessions() {
        return votingAgendaQueryRepository.getExpiredSessionsResult();
    }

    public List<VoteCountResult> getVoteCountResults(String votingAgendaId) {
        return votingAgendaQueryRepository.getVoteCountResults(votingAgendaId);
    }

    public boolean memberHasVoted(String votingAgendaId, String memberId) {
        return votingAgendaRepository.memberHasAlreadyVotedInSession(votingAgendaId, memberId);
    }

    public VotingAgenda saveVotingAgenda(VotingAgenda votingAgenda) {
        return votingAgendaRepository.save(votingAgenda);
    }

    public List<VotingAgenda> listVotingAgendas() {
        return votingAgendaRepository.findAll();
    }

    public void deleteVotingAgenda(String votingAgendaId) {
        votingAgendaRepository.deleteById(votingAgendaId);
    }

    public boolean votingAgendaDoesNotExist(String votingAgendaId) {
        return !votingAgendaRepository.existsById(votingAgendaId);
    }

    public VotingAgenda findVotingAgendaById(String votingAgendaId) {
        return votingAgendaRepository.findById(votingAgendaId)
                .orElseThrow(() -> new NotFoundException("not_found_voting_agenda", votingAgendaId));
    }
}
