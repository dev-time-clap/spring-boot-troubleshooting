package de.devtime.examples.library.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.devtime.examples.library.api.contract.EndpointConstants;
import de.devtime.examples.library.api.contract.request.BookRegistrationRequestDto;
import de.devtime.examples.library.businesslogic.BookService;
import de.devtime.examples.library.event.BookRegistrationRequestedEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BookRestController {

  @Setter(onMethod_ = @Autowired)
  private BookService bookService;

  @Setter(onMethod_ = @Autowired)
  private ApplicationContext appContext;

  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION)
  @ResponseStatus(HttpStatus.CREATED)
  public void registerBook(@RequestBody final BookRegistrationRequestDto registrationDto) {
    log.info("A book registration was requested with the following data: {}", registrationDto);
    BookRegistrationRequestedEvent.builder()
        .withBook(registrationDto.getBook())
        .withAuthor(registrationDto.getAuthor())
        .withPublisher(registrationDto.getPublisher())
        .fire();
  }
}
