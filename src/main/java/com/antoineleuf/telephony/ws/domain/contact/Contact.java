package com.antoineleuf.telephony.ws.domain.contact;

public record Contact(
  String id,
  String telephoneNumber,
  String address,
  String name
) {}
