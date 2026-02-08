package de.devtime.examples.library.event;

import de.devtime.examples.library.api.contract.author.AuthorRegistrationDto;
import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.api.contract.publisher.PublisherRegistrationDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

public class BookRegistrationRequestedEvent extends AbstractEvent {

  @Getter
  private final BookRegistrationDto book;

  @Getter
  private final AuthorRegistrationDto author;

  @Getter
  private final PublisherRegistrationDto publisher;

  @Builder(setterPrefix = "with")
  public BookRegistrationRequestedEvent(
      final BookRegistrationDto book,
      final AuthorRegistrationDto author,
      final PublisherRegistrationDto publisher) {
    super();
    this.book = book;
    this.author = author;
    this.publisher = publisher;
  }

  @Slf4j
  public static class BookRegistrationRequestedEventBuilder
      extends AbstractManualAutowiredBean<BookRegistrationRequestedEventBuilder> {

    public void fire() {
      BookRegistrationRequestedEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
