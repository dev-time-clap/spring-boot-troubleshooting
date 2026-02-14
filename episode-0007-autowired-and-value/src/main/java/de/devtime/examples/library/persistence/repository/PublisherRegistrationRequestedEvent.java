package de.devtime.examples.library.persistence.repository;

import de.devtime.examples.library.api.contract.publisher.PublisherRegistrationDto;
import de.devtime.examples.library.context.AbstractManualAutowiredBean;
import de.devtime.examples.library.event.AbstractEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublisherRegistrationRequestedEvent extends AbstractEvent {

  @Getter
  private PublisherRegistrationDto publisher;

  @Builder(setterPrefix = "with")
  public PublisherRegistrationRequestedEvent(
      final PublisherRegistrationDto publisher) {
    super();
    this.publisher = publisher;
  }

  public static class PublisherRegistrationRequestedEventBuilder
      extends AbstractManualAutowiredBean<PublisherRegistrationRequestedEventBuilder> {

    public void fire() {
      PublisherRegistrationRequestedEvent eventToFire = build();
      log.debug("Fire event {}", eventToFire);
      this.appContext.publishEvent(eventToFire);
    }
  }
}
