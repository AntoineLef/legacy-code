package com.antoineleuf.conf.legacy.ws.domain.md;

import java.time.Duration;
import java.time.LocalDateTime;

public class WeekDaysPayCalculator implements PayCalculator {

  @Override
  public Double calculatePayRatioFrom(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    Duration duration = Duration.between(startDateTime, endDateTime);

    if (startDateTime.isAfter(endDateTime)) {
      duration = Duration.between(startDateTime, endDateTime.plusHours(24));
    }

    return 600 * (duration.toHours() / 8.0);
  }

}
