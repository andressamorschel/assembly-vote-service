package com.assembly.vote.service.repository;

import com.assembly.vote.service.domain.VotingAgenda;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingAgendaRepository extends MongoRepository<VotingAgenda, String> {

    @Query(value = "{ _id: ?0, votes: { $exists: true }, 'votes.memberId': ?1 }", exists = true)
    boolean memberHasAlreadyVotedInSession(String votingAgendaId, String memberId);
}
