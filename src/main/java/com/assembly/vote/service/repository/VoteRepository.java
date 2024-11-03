package com.assembly.vote.service.repository;

import com.assembly.vote.service.domain.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    @Query(value = "{ memberId: ?0, votingSessionId: ?1 }", exists = true)
    boolean memberHasAlreadyVotedInSession(String memberId, String votingSessionId);
}
