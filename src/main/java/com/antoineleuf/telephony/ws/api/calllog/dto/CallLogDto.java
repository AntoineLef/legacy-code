package com.antoineleuf.telephony.ws.api.calllog.dto;

public record CallLogDto(
  String id,
  String telephoneNumber,
  String date,
  int durationInSeconds
) {}
