package com.antoineleuf.conf.legacy.ws.domain.md;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

public class WeekDaysPayCalculatorTest {
  private static final Double CHARGE_FOR_EIGHT_HOURS = 600.0;
  private WeekDaysPayCalculator calculator = new WeekDaysPayCalculator();

  @Test
  public void given8HoursWorked_whenCalculatingPayForWorkedHours_thenPayIsFull() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-05T10:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T18:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isEqualTo(CHARGE_FOR_EIGHT_HOURS);
  }

  @Test
  public void given1HourWorked_whenCalculatingPayForWorkedHours_thenPayIsOneEightOfFullDay() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-05T10:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T11:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isEqualTo(CHARGE_FOR_EIGHT_HOURS / 8);
  }

  @Test
  public void givenZeroHourWorked_whenCalculatingPayForWorkedHours_thenPayIsZero() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-05T10:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T10:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isEqualTo(0.0);
  }

  @Test
  public void givenMoreThanEightHoursWorked_whenCalculatingPayForWorkedHours_thenPayIsMoreThanFullDay() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-05T10:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T20:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isEqualTo(CHARGE_FOR_EIGHT_HOURS * 1.25);
  }

  @Test
  public void givenEightHoursWorkedBetweenTwoDays_whenCalculatingPayForWorkedHours_thenPayIsFullDay() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-04T20:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T04:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isEqualTo(CHARGE_FOR_EIGHT_HOURS);
  }
}
