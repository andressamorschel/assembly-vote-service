package com.assembly.vote.service.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.assembly.vote.service.repository.result.VotingResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SQSServiceTest {

    @Mock
    private AmazonSQS sqs;

    @Mock
    private ObjectMapper serializer;

    @InjectMocks
    private SQSService sqsService;

    @Captor
    private ArgumentCaptor<SendMessageRequest> sendMessageRequestCaptor;

    @Test
    void shouldSendMessageSuccessfully() throws JsonProcessingException {
        setField(sqsService, "queue", "queue-url");
        var votingResult = mock(VotingResult.class);
        given(serializer.writeValueAsString(votingResult)).willReturn("voting result");

        sqsService.sendMessage(votingResult);

        verify(sqs).sendMessage(sendMessageRequestCaptor.capture());
        verify(serializer).writeValueAsString(votingResult);

        var sendMessageRequestCaptorValue = sendMessageRequestCaptor.getValue();

        assertThat(sendMessageRequestCaptorValue.getMessageBody()).isEqualTo("voting result");
        assertThat(sendMessageRequestCaptorValue.getDelaySeconds()).isEqualTo(5);
        assertThat(sendMessageRequestCaptorValue.getQueueUrl()).isEqualTo("queue-url");
    }
}
