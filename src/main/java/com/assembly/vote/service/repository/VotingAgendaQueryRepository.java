package com.assembly.vote.service.repository;

import static java.time.LocalTime.now;
import static org.springframework.data.mongodb.core.BulkOperations.BulkMode.UNORDERED;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.assembly.vote.service.domain.VotingAgenda;
import com.assembly.vote.service.repository.result.VoteCountResult;
import com.assembly.vote.service.repository.result.VotingResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VotingAgendaQueryRepository {

    private static final String COUNT = "count";
    private static final String VOTE = "vote";

    private static final String TITLE = "title";

    private static final String DESCRIPTION = "description";

    private static final String VOTE_FIELD = "votes.vote";

    private static final String YES_COUNT = "yesCount";

    private static final String NO_COUNT = "noCount";

    private static final String ID = "_id";

    private final MongoOperations mongoOperations;

    public void markVotingAgendasAsNotified(List<String> votingAgendaIds) {
        var criteria = where("_id").in(votingAgendaIds);

        var update = new Update();
        update.set("votingSession.notificationSent", true);

        mongoOperations.bulkOps(UNORDERED, VotingAgenda.class)
                .updateMulti(new Query(criteria), update)
                .execute();
    }

    public List<VotingResult> getExpiredSessionsResult() {
        var aggregation = newAggregation(
                buildMatchOperationForExpiredSessions(),
                buildUnwindOperation(),
                buildGroupOperationForExpiredSessions(),
                buildProjectOperationForExpiredSessions()
        );

        return mongoOperations.aggregate(aggregation, VotingAgenda.class, VotingResult.class).getMappedResults();
    }

    private MatchOperation buildMatchOperationForExpiredSessions() {
        var lastExpiredSessionsCriteria = where("votingSession.end").lte(now())
                .and("votingSession.notificationSent").is(false);
        return match(lastExpiredSessionsCriteria);
    }

    private ProjectionOperation buildProjectOperationForExpiredSessions() {
        return project()
                .and(ID).as("id")
                .and(TITLE).as(TITLE)
                .and(DESCRIPTION).as(DESCRIPTION)
                .and(YES_COUNT).as("result.yes")
                .and(NO_COUNT).as("result.no");
    }

    private GroupOperation buildGroupOperationForExpiredSessions() {
        return group("id")
                .first(TITLE).as(TITLE)
                .first(DESCRIPTION).as(DESCRIPTION)
                .sum(when(where(VOTE_FIELD).is("YES")).then(1).otherwise(0)).as(YES_COUNT)
                .sum(when(where(VOTE_FIELD).is("NO")).then(1).otherwise(0)).as(NO_COUNT);
    }

    public List<VoteCountResult> getVoteCountResults(String votingAgendaId) {
        var aggregation = newAggregation(
                buildMatchOperation(votingAgendaId),
                buildUnwindOperation(),
                buildCountOperation(),
                buildProjectionOperation()
        );

        return mongoOperations.aggregate(aggregation, VotingAgenda.class, VoteCountResult.class).getMappedResults();
    }

    private MatchOperation buildMatchOperation(String votingAgendaId) {
        var criteria = where(ID).is(votingAgendaId);
        return match(criteria);
    }

    private UnwindOperation buildUnwindOperation() {
        return unwind("votes");
    }

    private ProjectionOperation buildProjectionOperation() {
        return project().and(COUNT).as(COUNT)
                .and(ID).as(VOTE);
    }

    private GroupOperation buildCountOperation() {
        return group(VOTE_FIELD).count().as(COUNT);
    }

}
