package com.antoineleuf.telephony.ws;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.antoineleuf.TelephonyWsMain;

import static io.restassured.RestAssured.get;

public class ContactResourceIT {
  @BeforeAll
  public static void globalSetUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @BeforeEach
  public void setUp() {
    Thread t = new Thread(() -> {
      try {
        TelephonyWsMain.main(new String[]{});
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    t.setDaemon(true);
    t.start();
  }

  @Test
  public void givenContacts_whenGetAllContacts_thenContactsReturned() {
    get("/api/telephony/contacts").then().body("name", Matchers.hasItem("Steve Jobs"));
  }
}
