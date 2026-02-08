package de.devtime.examples.library.persistence.repository;

import de.devtime.examples.library.api.contract.book.BookDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import de.devtime.examples.library.event.AbstractEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookRegisteredEvent extends AbstractEvent {

  @Getter
  private BookDto book;

  @Builder(setterPrefix = "with")
  public BookRegisteredEvent(final BookDto book) {
    super();
    this.book = book;
  }

  public static class BookRegisteredEventBuilder
      extends AbstractManualAutowiredBean<BookRegisteredEventBuilder> {

    public void fire() {
      BookRegisteredEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
