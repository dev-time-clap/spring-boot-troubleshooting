package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import de.devtime.examples.library.persistence.repository.AuthorRegisteredEvent;
import de.devtime.examples.library.persistence.repository.AuthorRegistrationRequestedEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorEventListener {

  @Setter(onMethod_ = @Autowired)
  private AuthorService authorService;

  @EventListener
  void onAuthorRegistrationRequestedEvent(final AuthorRegistrationRequestedEvent event) {
    this.authorService.registerAuthor(event.getAuthor());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  void onAuthorRegisteredEvent(final AuthorRegisteredEvent event) {
    log.info("Author was successfully registered with the following data: {}", event.getAuthor());
  }
}
