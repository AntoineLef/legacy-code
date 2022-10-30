package com.antoineleuf.telephony.ws.api.contact.dto;

public record ContactDto(
   String id,
   String telephoneNumber,
   String address,
   String name
) {}
