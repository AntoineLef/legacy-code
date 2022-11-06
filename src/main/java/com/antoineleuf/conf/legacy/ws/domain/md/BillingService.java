package com.antoineleuf.conf.legacy.ws.domain.md;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class BillingService {

  private static final double JOUR = 8.0;

  public double dailyTotalOf(String id, LocalDate date) throws SQLException {
    Connection c = null;
    PreparedStatement st = null;

    c = DriverManager.getConnection("jdbc:postgresql://localhost:49153/doctordb", "postgres", "postgrespw");

    c.setAutoCommit(false);

    st = c.prepareStatement("Select * from PROCEDURES");
    ResultSet r = st.executeQuery();

    Double l_valeur = 0.0;

    while (r.next()) {

      if (r.getString("d_id").equals(id)) {
        if (r.getDate("p_date").toLocalDate().isEqual(date)) {
          Duration duree = Duration.between(LocalTime.parse(r.getString("s_time")),
                                            LocalTime.parse(r.getString("s_time")));
          l_valeur += 600 * (duree.toHours() / 8.0);
        }
      }
    }

    return l_valeur;
  }

}
