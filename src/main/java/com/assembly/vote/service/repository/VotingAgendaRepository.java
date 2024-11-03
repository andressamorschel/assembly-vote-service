package com.assembly.vote.service.repository;

import com.assembly.vote.service.domain.VotingAgenda;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingAgendaRepository extends MongoRepository<VotingAgenda, String> {
}
