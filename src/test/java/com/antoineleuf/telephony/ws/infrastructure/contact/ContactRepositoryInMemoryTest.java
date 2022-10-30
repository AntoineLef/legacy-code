package com.antoineleuf.telephony.ws.infrastructure.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.antoineleuf.telephony.ws.domain.contact.Contact;
import com.antoineleuf.telephony.ws.infrastructure.contact.ContactRepositoryInMemory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ContactRepositoryInMemoryTest {
  private static final String CONTACT_ID = "id";
  public static final String TELEPHONE_NUMBER = "phoneNumber";
  public static final String ADDRESS = "address";
  public static final String CONTACT_NAME = "contactName";

  private ContactRepositoryInMemory contactRepositoryInMemory;

  @BeforeEach
  public void setUp() {
    contactRepositoryInMemory = new ContactRepositoryInMemory();
  }

  @Test
  public void givenContact_whenFindAll_ThenReturnContactInMemory() {
    //given
    Contact contact = new Contact(CONTACT_ID, TELEPHONE_NUMBER, ADDRESS, CONTACT_NAME);
    contactRepositoryInMemory.save(contact);

    // when
    List<Contact> contacts = contactRepositoryInMemory.findAll();

    // then
    assertThat(contacts).containsExactly(contact);
  }
}
