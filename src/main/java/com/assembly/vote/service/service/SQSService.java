package com.assembly.vote.service.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.assembly.vote.service.exception.InternalServerErrorException;
import com.assembly.vote.service.repository.result.VotingResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSService {

    private final AmazonSQS sqs;

    private final ObjectMapper serializer;

    @Value("${vote.queue}")
    private String queue;

    public void sendMessage(VotingResult votingResult) {
        try {
            var serializedMessage = serializer.writeValueAsString(votingResult);
            var messageRequest = new SendMessageRequest()
                    .withQueueUrl(queue)
                    .withMessageBody(serializedMessage)
                    .withDelaySeconds(5);
            sqs.sendMessage(messageRequest);
        } catch (JsonProcessingException e) {
            log.info("Error trying to write SQS message. {}", e.getMessage());
            throw new InternalServerErrorException("internal_server_error");
        }
    }
}
