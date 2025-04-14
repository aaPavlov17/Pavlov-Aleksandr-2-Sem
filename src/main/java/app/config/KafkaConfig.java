package app.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

  @Value("${audit-topic}")
  private String auditTopic;

  @Bean
  public NewTopic auditTopic() {
    return TopicBuilder.name(auditTopic)
        .partitions(1)
        .replicas(1)
        .build();
  }
}
