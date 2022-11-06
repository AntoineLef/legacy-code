package com.antoineleuf.conf.legacy.ws.domain.md;

import java.time.LocalDateTime;

public interface PayCalculator {

  Double calculatePayRatioFrom(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
