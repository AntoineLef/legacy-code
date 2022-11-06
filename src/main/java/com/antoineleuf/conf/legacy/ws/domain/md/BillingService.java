package com.antoineleuf.conf.legacy.ws.domain.md;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.antoineleuf.conf.legacy.DoctorBillingMain;

public class BillingService {

  public double dailyTotalOf(String id, LocalDate date) throws SQLException {
    Connection c = null;
    PreparedStatement st = null;

    c = DriverManager.getConnection(DoctorBillingMain.DB_URL, "demo-account", "AVNS_NUij4N-r65Ff24ge_Un");

    c.setAutoCommit(false);

    st = c.prepareStatement("Select * from PROCEDURES");
    ResultSet r = st.executeQuery();

    Double l_valeur = 0.0;

    while (r.next()) {

      if (r.getString("d_id").equals(id)) {
        if (r.getDate("p_date").toLocalDate().isEqual(date)) {
          String s_time = r.getString("s_time");
          String e_time = r.getString("e_time");
          LocalDateTime sDT = LocalDateTime.parse(r.getDate("p_date") + "T" + LocalTime.parse(s_time));
          LocalDateTime eDT = LocalDateTime.parse(r.getDate("p_date") + "T" + LocalTime.parse(e_time));

          l_valeur += calculatePayRatioFromDay(s_time, e_time, sDT, eDT);
        }
      }
    }

    return l_valeur;
  }

  private Double calculatePayRatioFromDay(String startTime,
                                          String endTime,
                                          LocalDateTime startDateTime,
                                          LocalDateTime endDateTime)
  {
    Duration duration = Duration.between(startDateTime, endDateTime);

    if (startDateTime.isAfter(endDateTime)) {
      duration = Duration.between(startDateTime, endDateTime.plusHours(24));
    }

    return 600 * (duration.toHours() / 8.0);
  }

}
