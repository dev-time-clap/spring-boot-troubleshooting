package de.devtime.examples.library;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.devtime.examples.library.businesslogic.BookService;

@SpringBootTest
class DemoBibliothekApplicationIT {

  @Autowired
  private BookService sut;

  @Test
  void testRegisterBookShouldReturnCorrectCountOfBooks() {
    int actualCount = this.sut.registerBook("ISBN-0815", "Just a book");

    assertThat(actualCount).isEqualTo(1);
  }

}
