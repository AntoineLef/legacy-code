package com.antoineleuf.conf.legacy.ws.domain.md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BillingServiceTest {

  private static PreparedStatement insert;
  private static Connection c;

  @BeforeAll
  public static void before() throws SQLException {
    c = DriverManager.getConnection("jdbc:postgresql://localhost:49153/doctordb", "postgres", "postgrespw");

    insert =
           c.prepareStatement("INSERT INTO procedures values ('TEST_PROCEDURE_ID', 'A_DOCTOR_ID', 'AN_HOSPITAL', ?, ?, ?);");

  }

  @AfterAll
  public static void tearDown() throws SQLException {
    c.close();
  }

  @AfterEach
  public void deleteTestRecord() throws SQLException {

    PreparedStatement deleteTestProcedure =
                                          c.prepareStatement("DELETE from procedures where p_id like 'TEST_PROCEDURE_ID';");
    deleteTestProcedure.execute();
  }

  @Test
  public void givenAFullDayWorked_whenCalculatingPay_thenPayIsFull() throws SQLException {
    // given
    createAFullDayOfWork();

    BillingService service = new BillingService();

    // when
    double dailyTotal = service.dailyTotalOf("A_DOCTOR_ID", LocalDate.now());

    // then
    assertEquals(600.0, dailyTotal);
  }

  private void createAFullDayOfWork() throws SQLException {
    insert.setString(1, "10:00:00");
    insert.setString(2, "18:00:00");
    insert.setString(3, LocalDate.now().toString());
    insert.executeUpdate();
  }

}
