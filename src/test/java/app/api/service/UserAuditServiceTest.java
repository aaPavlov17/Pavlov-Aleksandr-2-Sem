package app.api.service;

import com.datastax.oss.driver.api.core.cql.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserAuditServiceTest {

  @Container
  private static final CassandraContainer cassandraContainer =
      new CassandraContainer("cassandra:4.1")
          .withExposedPorts(9042)
          .waitingFor(Wait.forListeningPort());

  @Autowired
  private UserAuditService userAuditService;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    String contactPoint =
        cassandraContainer.getHost() + ":" + cassandraContainer.getMappedPort(9042);
    registry.add("spring.cassandra.contact-points", () -> contactPoint);
    registry.add("spring.cassandra.local-datacenter", () -> "datacenter1");
    registry.add("spring.cassandra.keyspace-name", () -> "my_keyspace");
  }

  @Test
  void shouldSuccessfullyInsertUserAction() {
    UUID userId = UUID.randomUUID();

    userAuditService.insertUserAction(userId, UserAuditService.Action.DROP_DATABASE);

    List<Row> result = userAuditService.getUserAudit(userId);

    Assertions.assertEquals(1, result.size());
  }

  @Test
  public void testGetUserById() {

    assertThrows(RuntimeException.class, () -> {
      userAuditService.insertUserAction(null, null);
    });
  }

}