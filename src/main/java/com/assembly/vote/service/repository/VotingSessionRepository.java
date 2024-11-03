package com.assembly.vote.service.repository;

import com.assembly.vote.service.domain.VotingSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingSessionRepository extends MongoRepository<VotingSession, String> {
}
