package de.devtime.examples.library.businesslogic;

import org.springframework.stereotype.Service;

import de.devtime.examples.library.api.contract.book.BookDto;
import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.repository.BookRegisteredEvent;
import de.devtime.examples.library.persistence.repository.BookRepository;
import de.devtime.examples.library.persistence.util.TransactionHelper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

  private final TransactionHelper txHelper;
  private final BookRepository bookRepo;

  public void registerBook(final BookRegistrationDto registrationDto) {
    BookEntity bookToRegister = BookEntity.builder()
        .withIsbn(registrationDto.getIsbn())
        .withTitle(registrationDto.getTitle())
        .withIsOnLoan(false)
        .buildAndInit();

    BookEntity savedEntity = this.txHelper.executeInTx(_ -> this.bookRepo.save(bookToRegister));

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
