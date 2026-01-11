package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.devtime.examples.library.api.contract.book.BookRegistrationDto;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.Setter;

@Service
public class BookService {

  @Setter(onMethod_ = @Autowired)
  private BookRepository bookRepo;

  @Transactional
  public void registerBook(final BookRegistrationDto registrationData) {

    BookEntity bookToRegister = BookEntity.builder()
        .withAuthor(registrationData.getAuthor())
        .withIsbn(registrationData.getIsbn())
        .withTitle(registrationData.getTitle())
        .withIsOnLoan(false)
        .buildAndInit();

    this.bookRepo.save(bookToRegister);

  }

}
