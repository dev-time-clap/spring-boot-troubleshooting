package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import de.devtime.examples.library.persistence.repository.PublisherRegisteredEvent;
import de.devtime.examples.library.persistence.repository.PublisherRegistrationRequestedEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PublisherEventListener {

  @Setter(onMethod_ = @Autowired)
  private PublisherService publisherService;

  @EventListener
  void onPublisherRegistrationRequestedEvent(final PublisherRegistrationRequestedEvent event) {
    this.publisherService.registerPublisher(event.getPublisher());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  void onPublisherRegisteredEvent(final PublisherRegisteredEvent event) {
    log.info("Publisher was successfully registered with the following data: {}", event.getPublisher());
  }
}
