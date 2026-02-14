package de.devtime.examples.library.persistence.repository;

import de.devtime.examples.library.api.contract.author.AuthorRegistrationDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import de.devtime.examples.library.event.AbstractEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorRegistrationRequestedEvent extends AbstractEvent {

  @Getter
  private AuthorRegistrationDto author;

  @Builder(setterPrefix = "with")
  public AuthorRegistrationRequestedEvent(
      final AuthorRegistrationDto author) {
    super();
    this.author = author;
  }

  public static class AuthorRegistrationRequestedEventBuilder
      extends AbstractManualAutowiredBean<AuthorRegistrationRequestedEventBuilder> {

    public void fire() {
      AuthorRegistrationRequestedEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
