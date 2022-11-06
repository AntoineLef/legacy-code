package com.antoineleuf.conf.legacy.ws.domain.md;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.google.common.truth.Truth;

public class WeekendPayCalculatorTest {
  private static final Double CHARGE_FOR_SIX_HOURS = 800.0;
  private WeekendPayCalculator calculator = new WeekendPayCalculator();

  @Test
  public void given6HoursWorked_whenCalculatingPayForWorkedHours_thenPayIsFull() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-05T10:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T16:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isEqualTo(CHARGE_FOR_SIX_HOURS);
  }

  @Test
  public void given1HourWorked_whenCalculatingPayForWorkedHours_thenPayIsOneEightOfFullDay() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-05T10:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T11:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isWithin(0.001).of(CHARGE_FOR_SIX_HOURS / 6);
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
  public void givenMoreThanSixHoursWorked_whenCalculatingPayForWorkedHours_thenPayIsMoreThanFullDay() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-05T10:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T18:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isWithin(0.001).of(CHARGE_FOR_SIX_HOURS * 4 / 3);
  }

  @Test
  public void givenSixHoursWorkedBetweenTwoDays_whenCalculatingPayForWorkedHours_thenPayIsFullDay() {
    // given
    LocalDateTime startDateTime = LocalDateTime.parse("2022-11-04T21:00:00");
    LocalDateTime endDateTime = LocalDateTime.parse("2022-11-05T03:00:00");

    // when
    Double amount = calculator.calculatePayRatioFrom(startDateTime, endDateTime);

    // then
    Truth.assertThat(amount).isEqualTo(CHARGE_FOR_SIX_HOURS);
  }
}
