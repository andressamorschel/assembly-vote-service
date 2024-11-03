package com.assembly.vote.service.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.assembly.vote.service.domain.Vote;
import com.assembly.vote.service.repository.result.VoteCountResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoteQueryRepository {

    private static final String COUNT = "count";
    private static final String VOTE = "vote";

    private final MongoOperations mongoOperations;

    public List<VoteCountResult> getVoteSessionResult(String votingSessionId) {
        var aggregation = newAggregation(matchBySessionId(votingSessionId), groupVote(), projectFields());

        return mongoOperations.aggregate(aggregation, Vote.class, VoteCountResult.class).getMappedResults();
    }

    private MatchOperation matchBySessionId(String votingSessionId) {
        return match(where("votingSessionId").is(votingSessionId));
    }

    private GroupOperation groupVote() {
        return group(VOTE)
                .count().as(COUNT);
    }

    private ProjectionOperation projectFields() {
        return project()
                .and(COUNT).as(COUNT)
                .and("_id").as(VOTE);
    }
}
