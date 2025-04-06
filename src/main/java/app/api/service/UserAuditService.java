package app.api.service;

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

  enum Action {
    SELECT, UPDATE, INSERT, DELETE, DROP_DATABASE
  }

  public void insertUserAction(UUID userId, Action action) {

    PreparedStatement preparedStatement = session.prepare(
        "INSERT INTO my_keyspace.user_audit (user_id, event_time, event_type, event_details) " +
            "VALUES (?, ?, ?, ?)"
    );

    BoundStatement boundStatement = preparedStatement.bind(
        userId,
        java.time.Instant.now(),
        action.toString(),
        "User did action " + action.toString()
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
