package com.assembly.vote.service.service;

import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.repository.VotingAgendaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotingAgendaService {

    private final VotingAgendaRepository votingAgendaRepository;

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
}
