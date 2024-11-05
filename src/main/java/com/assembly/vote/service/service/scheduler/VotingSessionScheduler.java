package com.assembly.vote.service.service.scheduler;

import com.assembly.vote.service.repository.result.VotingResult;
import com.assembly.vote.service.service.SQSService;
import com.assembly.vote.service.service.VotingAgendaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotingSessionScheduler {

    private final SQSService sqsService;

    private final VotingAgendaService votingAgendaService;

    @Scheduled(fixedRate = 3600000)
    public void processExpiredSessions() {
        var expiredSessions = votingAgendaService.findExpiredSessions();

        expiredSessions.forEach(this::handleExpiredSession);

        votingAgendaService.markAsNotificationSent(getVotingAgendaIds(expiredSessions));
    }

    private List<String> getVotingAgendaIds(List<VotingResult> votingResults) {
        return votingResults.stream()
                .map(VotingResult::id)
                .toList();
    }

    private void handleExpiredSession(VotingResult votingResult) {
        log.info("Handling Expired Voting Session: {}", votingResult);
        sqsService.sendMessage(votingResult);
    }
}
