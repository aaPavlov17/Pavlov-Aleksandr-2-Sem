package app.config.customendpointactuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Endpoint(id = "random-uuid")
public class CustomEndpoint {

  @ReadOperation
  public String generateUuid() {
    return UUID.randomUUID().toString();
  }
}
