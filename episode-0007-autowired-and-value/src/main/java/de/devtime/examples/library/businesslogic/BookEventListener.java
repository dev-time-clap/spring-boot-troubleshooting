package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import de.devtime.examples.library.event.BookRegistrationRequestedEvent;
import de.devtime.examples.library.persistence.repository.AuthorRegistrationRequestedEvent;
import de.devtime.examples.library.persistence.repository.BookRegisteredEvent;
import de.devtime.examples.library.persistence.repository.PublisherRegistrationRequestedEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookEventListener {

  @Setter(onMethod_ = @Autowired)
  private BookService bookService;

  @EventListener
  void onBookRegistrationRequestedEvent(final BookRegistrationRequestedEvent event) {
    AuthorRegistrationRequestedEvent.builder()
        .withAuthor(event.getAuthor())
        .fire();
    PublisherRegistrationRequestedEvent.builder()
        .withPublisher(event.getPublisher())
        .fire();

    this.bookService.registerBook(event.getBook());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  void onBookRegisteredEvent(final BookRegisteredEvent event) {
    log.info("Book was successfully registered with the following data: {}", event.getBook());
  }
}
