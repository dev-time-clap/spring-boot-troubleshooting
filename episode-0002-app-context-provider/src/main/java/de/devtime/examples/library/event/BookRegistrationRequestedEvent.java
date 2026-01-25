package de.devtime.examples.library.event;

import org.springframework.context.ApplicationEvent;

import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookRegistrationRequestedEvent extends ApplicationEvent {

  private static final long serialVersionUID = 7735785840873294529L;

  @Getter
  private final BookRegistrationDto registrationData;

  @Builder(setterPrefix = "with")
  public BookRegistrationRequestedEvent(final Object source, final BookRegistrationDto registrationData) {
    super(source);
    this.registrationData = registrationData;
  }

  public static class BookRegistrationRequestedEventBuilder
      extends AbstractManualAutowiredBean<BookRegistrationRequestedEventBuilder> {

    public void fire() {
      autowire();
      BookRegistrationRequestedEvent eventToFire = build();
      log.info("A book registration was requested with the following data: {}", eventToFire.getRegistrationData());
      this.appContext = null;
      this.appContext.publishEvent(eventToFire);
    }
  }
}
