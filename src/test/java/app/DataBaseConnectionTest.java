package app;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class DataBaseConnectionTest {

  @Container
  public static PostgreSQLContainer<?> postgesContainer = new PostgreSQLContainer<>("postgres:13")
      .withDatabaseName("testdb")
      .withUsername("testUser")
      .withPassword("testPassword")
      .withInitScript("init.sql");


  @Test
  public void testDatabaseConnection() {
    String jdbcUrl = postgesContainer.getJdbcUrl();
    String host = postgesContainer.getHost();
    Integer port = postgesContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT);
    log.info("jdbcUrl={}", jdbcUrl);
    log.info("host={}", host);
    log.info("port={}", port);
    assertTrue(postgesContainer.isRunning());
  }
}
