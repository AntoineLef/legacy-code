package com.antoineleuf.telephony.ws.domain.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.antoineleuf.telephony.ws.api.contact.dto.ContactDto;
import com.antoineleuf.telephony.ws.domain.contact.Contact;
import com.antoineleuf.telephony.ws.domain.contact.ContactAssembler;
import com.antoineleuf.telephony.ws.domain.contact.ContactRepository;
import com.antoineleuf.telephony.ws.domain.contact.ContactService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {
  public static final String ID = "id";
  public static final String TELEPHONE_NUMBER = "somePhoneNumber";
  public static final String ADDRESS = "address";
  public static final String NAME = "User Name";

  private ContactService contactService;

  @Mock
  private ContactRepository contactRepository;

  @BeforeEach
  public void setUp() {
    contactService = new ContactService(contactRepository, new ContactAssembler());
  }

  @Test
  public void givenContactsInRepository_whenFindAllContacts_thenReturnDtos() {
    // given
    Contact contact = new Contact(ID, TELEPHONE_NUMBER, ADDRESS, NAME);
    given(contactRepository.findAll()).willReturn(List.of(contact));

    // when
    List<ContactDto> contactDtos = contactService.findAllContacts();

    // then
    assertThat(contactDtos).contains(new ContactDto(ID,TELEPHONE_NUMBER,ADDRESS, NAME));
  }
}
