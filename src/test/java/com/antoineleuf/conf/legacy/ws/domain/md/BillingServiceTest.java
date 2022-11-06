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

  private static final LocalDate TODAY = LocalDate.now();
  private static final String A_DOCTOR_ID = "A_DOCTOR_ID";
  private static PreparedStatement insert;
  private static Connection c;
  private BillingService service = new BillingService();

  @BeforeAll
  public static void before() throws SQLException {
    c = DriverManager.getConnection("jdbc:postgresql://localhost:49153/doctordb", "postgres", "postgrespw");

    insert = c.prepareStatement("INSERT INTO procedures values ('TEST_PROCEDURE_ID', 'A_DOCTOR_ID',"
                                + " 'AN_HOSPITAL', ?, ?, ?);");

  }

  @AfterAll
  public static void tearDown() throws SQLException {
    c.close();
  }

  @AfterEach
  public void deleteTestRecord() throws SQLException {

    PreparedStatement deleteTestProcedure = c.prepareStatement("DELETE from procedures where p_id like "
                                                               + "'TEST_PROCEDURE_ID';");
    deleteTestProcedure.execute();
  }

  @Test
  public void givenAFullDayWorked_whenCalculatingPayForToday_thenPayIsFull() throws SQLException {
    // given
    createAFullDayOfWork();

    // when
    double dailyTotal = service.dailyTotalOf(A_DOCTOR_ID, TODAY);

    // then
    assertEquals(600.0, dailyTotal);
  }

  @Test
  public void givenAHalfDayWorked_whenCalculatingPayForToday_thenPayIsHalf() throws SQLException {
    // given
    createAHalfDayOfWork();

    // when
    double dailyTotalOf = service.dailyTotalOf(A_DOCTOR_ID, TODAY);

    // then
    assertEquals(300.0, dailyTotalOf);
  }

  @Test
  public void givenAFullDayWorkedYesterday_whenCalculatingPayForToday_thenPayIsZero() throws SQLException {
    // given
    createAFullDayOfWorkYesterday();

    // when
    double dailyTotalOf = service.dailyTotalOf(A_DOCTOR_ID, TODAY);

    // then
    assertEquals(0.0, dailyTotalOf);
  }

  @Test
  public void givenAFullDayWorkedBetweenTwoDay_whenCalculatingPayForToday_thenPayIsZeroSinceItStartedThePreviousDay()
    throws SQLException
  {
    // given
    createFullDaySharedBetweenTwoDay();

    // when
    double dailyTotalOf = service.dailyTotalOf(A_DOCTOR_ID, TODAY);

    // then
    assertEquals(0.0, dailyTotalOf);
  }

  @Test
  public void givenAFullDayWorkedBetweenTwoDay_whenCalculatingPayForYesterday_thenPayIsFull() throws SQLException {
    // given
    createFullDaySharedBetweenTwoDay();

    // when
    double dailyTotalOf = service.dailyTotalOf(A_DOCTOR_ID, TODAY.minusDays(1));

    // then
    assertEquals(600.0, dailyTotalOf);
  }

  private void createFullDaySharedBetweenTwoDay() throws SQLException {
    insert.setString(1, "20:00:00");
    insert.setString(2, "04:00:00");
    insert.setString(3, TODAY.minusDays(1).toString());
    insert.executeUpdate();
  }

  private void createAFullDayOfWorkYesterday() throws SQLException {
    insert.setString(1, "10:00:00");
    insert.setString(2, "18:00:00");
    insert.setString(3, TODAY.minusDays(1).toString());
    insert.executeUpdate();
  }

  private void createAHalfDayOfWork() throws SQLException {
    insert.setString(1, "10:00:00");
    insert.setString(2, "14:00:00");
    insert.setString(3, TODAY.toString());
    insert.executeUpdate();
  }

  private void createAFullDayOfWork() throws SQLException {
    insert.setString(1, "10:00:00");
    insert.setString(2, "18:00:00");
    insert.setString(3, TODAY.toString());
    insert.executeUpdate();
  }

}
