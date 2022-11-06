package com.antoineleuf.conf.legacy.ws.domain.md;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class PayCalculatorSelector {

  public PayCalculator selectfrom(LocalDateTime startDateTime) {
    DayOfWeek dayOfWeek = startDateTime.getDayOfWeek();

    if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY))
      return new WeekendPayCalculator();
    return new WeekDaysPayCalculator();
  }

}
