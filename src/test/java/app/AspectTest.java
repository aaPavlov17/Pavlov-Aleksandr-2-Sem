package app;

import app.config.LoggingAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AspectTest {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private LoggingAspect loggingAspect;

  @LocalServerPort
  private int port;

  @Test
  public void test() {
    assertEquals(0, loggingAspect.getCount());
    restTemplate.getForEntity("http://localhost:" + port + "/articles/get_article/1", HashMap.class);
    assertEquals(2, loggingAspect.getCount());
  }
}