package de.devtime.examples.library.persistence.repository;

import de.devtime.examples.library.api.contract.author.AuthorDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import de.devtime.examples.library.event.AbstractEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorRegisteredEvent extends AbstractEvent {

  @Getter
  private AuthorDto author;

  @Builder(setterPrefix = "with")
  public AuthorRegisteredEvent(final AuthorDto author) {
    super();
    this.author = author;
  }

  public static class AuthorRegisteredEventBuilder
      extends AbstractManualAutowiredBean<AuthorRegisteredEventBuilder> {

    public void fire() {
      AuthorRegisteredEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
