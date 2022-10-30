package com.antoineleuf.telephony.ws.infrastructure.contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.antoineleuf.telephony.ws.domain.contact.Contact;
import com.antoineleuf.telephony.ws.domain.contact.ContactNotFoundException;
import com.antoineleuf.telephony.ws.domain.contact.ContactRepository;

public class ContactRepositoryInMemory implements ContactRepository {

  private Map<String, Contact> contacts = new HashMap<>();

  @Override
  public List<Contact> findAll() {
    return contacts.values().stream().toList();
  }

  @Override
  public Contact findById(String id) {
    return contacts.get(id);
  }

  @Override
  public void update(Contact contact) throws ContactNotFoundException {
    Contact foundContact = contacts.get(contact.id());
    if (foundContact != null) {
      contacts.put(contact.id(), contact);
    } else {
      throw new ContactNotFoundException("Contact not found, cannot be updated");
    }
  }

  @Override
  public void save(Contact contact) {
    contacts.put(contact.id(), contact);
  }

  @Override
  public void remove(String id) {
    contacts.remove(id);
  }
}
