package com.antoineleuf.telephony.ws.domain.calllog;

public record CallLog(
  String id,
  String telephoneNumber,
  String date,
  int durationInSeconds
) {}
