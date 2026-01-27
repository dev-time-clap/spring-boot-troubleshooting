package de.devtime.examples.library.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.api.impl.BookRestController;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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

    @Setter(onMethod_ = @Autowired)
    private BookRestController controller;

    public void fire() {
      BookRegistrationRequestedEvent eventToFire = build();
      log.info("A book registration was requested with the following data: {}", eventToFire.getRegistrationData());
      log.info("controller: {}", this.controller);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
