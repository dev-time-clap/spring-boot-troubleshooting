package de.devtime.examples.library.persistence.repository;

import de.devtime.examples.library.api.contract.publisher.PublisherDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import de.devtime.examples.library.event.AbstractEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublisherRegisteredEvent extends AbstractEvent {

  @Getter
  private PublisherDto publisher;

  @Builder(setterPrefix = "with")
  public PublisherRegisteredEvent(final PublisherDto publisher) {
    super();
    this.publisher = publisher;
  }

  public static class PublisherRegisteredEventBuilder
      extends AbstractManualAutowiredBean<PublisherRegisteredEventBuilder> {

    public void fire() {
      PublisherRegisteredEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
