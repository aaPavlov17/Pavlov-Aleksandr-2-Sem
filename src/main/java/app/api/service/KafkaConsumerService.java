package app.api.service;

import app.api.dto.DtoMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

  private final ObjectMapper objectMapper;
  private final UserAuditService userAuditService;

  @KafkaListener(topics = {"${topic-to-consume-message}"})
  public void consumeMessage(String message) throws Exception {
    DtoMessage parsedMessage = objectMapper.readValue(message, DtoMessage.class);
    LOGGER.info("Retrieved message {}", message);
    userAuditService.insertUserAction(parsedMessage);
  }
}

