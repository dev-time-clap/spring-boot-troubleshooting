package de.devtime.examples.library.api.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.devtime.examples.library.api.contract.EndpointConstants;
import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.event.BookRegistrationRequestedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BookRestController {

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION)
  @ResponseStatus(HttpStatus.CREATED)
  public void registerBook(@RequestBody final BookRegistrationDto registrationDto) {

    BookRegistrationRequestedEvent.builder()
        .withSource(this)
        .withRegistrationData(registrationDto)
        .fire();

  }

}
