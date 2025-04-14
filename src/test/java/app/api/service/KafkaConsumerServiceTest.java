package app.api.service;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import app.api.dto.DtoMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(
    classes = {KafkaConsumerService.class},
    properties = {
        "topic-to-consume-message=audit-topic",
        "spring.kafka.consumer.group-id=some-consumer-group",
        "spring.kafka.consumer.auto-offset-reset=earliest"
    })
@Import({KafkaAutoConfiguration.class, KafkaConsumerServiceTest.ObjectMapperTestConfig.class})
@Testcontainers
class KafkaConsumerServiceTest {

  @TestConfiguration
  static class ObjectMapperTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
      return new ObjectMapper();
    }
  }

  @Container
  @ServiceConnection
  public static final KafkaContainer KAFKA =
      new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  @MockitoBean
  private UserAuditService userAuditService;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldSendMessageToKafkaSuccessfully()
      throws JsonProcessingException, ExecutionException, InterruptedException {
    DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID(), "test", "test");
    String messageJson = objectMapper.writeValueAsString(dtoMessage);

    kafkaTemplate.send("audit-topic", messageJson).get();

    await().atMost(Duration.ofSeconds(5))
        .pollDelay(Duration.ofSeconds(1))
        .untilAsserted(() -> Mockito.verify(
                userAuditService, times(1))
            .insertUserAction(eq(dtoMessage))
        );
  }

  @Test
  void shouldNotSendMessage()
      throws JsonProcessingException, ExecutionException, InterruptedException {
    DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID(), "test", "test");
    String messageJson = objectMapper.writeValueAsString(dtoMessage);

    kafkaTemplate.send("bad", messageJson).get();

    await().atMost(Duration.ofSeconds(5))
        .pollDelay(Duration.ofSeconds(1))
        .untilAsserted(() -> Mockito.verify(
                userAuditService, times(0))
            .insertUserAction(eq(dtoMessage))
        );
  }
}