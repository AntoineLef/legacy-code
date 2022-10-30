package com.antoineleuf.telephony.ws.infrastructure.contact;

import java.util.List;

import com.antoineleuf.telephony.ws.domain.contact.Contact;

public class ContactDevDataFactory {

  public List<Contact> createMockData() {
    Contact jobs = new Contact("1", "514-999-0000", "California", "Steve Jobs");
    Contact balmer = new Contact("2", "781-888-1111", "Manitoba", "Steve Balmer");
    Contact franklin = new Contact("3", "964-543-6475", "Washington", "Benjamin Franklin");

    return List.of(jobs, balmer, franklin);
  }
}
