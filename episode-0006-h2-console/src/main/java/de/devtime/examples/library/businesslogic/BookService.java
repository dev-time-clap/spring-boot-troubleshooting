package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.examples.library.api.contract.book.BookDto;
import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.repository.BookRegisteredEvent;
import de.devtime.examples.library.persistence.repository.BookRepository;
import lombok.Setter;

@Service
public class BookService {

  @Setter(onMethod_ = @Autowired)
  private BookRepository bookRepo;

  @Transactional
  public void registerBook(final BookRegistrationDto registrationDto) {
    BookEntity bookToRegister = BookEntity.builder()
        .withIsbn(registrationDto.getIsbn())
        .withTitle(registrationDto.getTitle())
        .withIsOnLoan(false)
        .buildAndInit();

    BookEntity savedEntity = this.bookRepo.save(bookToRegister);

    BookRegisteredEvent.builder()
        // XXX EntityToDtoMapper erstellen und hier nutzen
        .withBook(BookDto.builder()
            .withId(savedEntity.getId())
            .withIsbn(savedEntity.getIsbn())
            .withTitle(savedEntity.getTitle())
            .build())
        .fire();
  }
}
