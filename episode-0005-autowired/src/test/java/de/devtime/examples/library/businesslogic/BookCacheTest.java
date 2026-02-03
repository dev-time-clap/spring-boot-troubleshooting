package de.devtime.examples.library.businesslogic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BookCacheTest {

  @Test
  void putBookShouldAddTheGivenBook() {
    String expectedIsbn = "ISbN-0815";
    String expectedTitle = "Just a book";

    BookCache sut = new BookCache();
    sut.putBook(expectedIsbn, expectedTitle);

    String actualTitle = sut.getBook(expectedIsbn);
    assertThat(actualTitle).isEqualTo(expectedTitle);
  }
}
