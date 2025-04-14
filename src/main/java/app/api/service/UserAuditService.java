package app.api.service;

import app.api.dto.DtoMessage;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserAuditService {

  @Autowired
  private CqlSession session;

  public void insertUserAction(DtoMessage message) {

    PreparedStatement preparedStatement = session.prepare(
        "INSERT INTO my_keyspace.user_audit (user_id, event_time, event_type, event_details) " +
            "VALUES (?, ?, ?, ?)"
    );

    BoundStatement boundStatement = preparedStatement.bind(
        message.getUserId(),
        java.time.Instant.now(),
        message.getEvent(),
        message.getMessage()
    );

    session.execute(boundStatement);
  }

  public List<Row> getUserAudit(UUID userId) {
    PreparedStatement preparedStatement = session.prepare(
        "SELECT * FROM my_keyspace.user_audit WHERE user_id = ?"
    );

    BoundStatement boundStatement = preparedStatement.bind(userId);
    ResultSet resultSet = session.execute(boundStatement);
    List<Row> rows = new ArrayList<>();
    for (Row row : resultSet) {
      rows.add(row);
    }
    return rows;
  }
}
