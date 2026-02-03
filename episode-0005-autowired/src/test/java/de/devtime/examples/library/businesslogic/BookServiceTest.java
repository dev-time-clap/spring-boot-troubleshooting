package de.devtime.examples.library.businesslogic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class BookServiceTest {

  @Test
  void testRegisterBookShouldReturnCorrectCountOfBooks() {
    BookCache mockedBookCache = mock(BookCache.class);
    StatisticService statisticService = new StatisticService();
    BookService sut = new BookService(mockedBookCache, statisticService);

    int actualCount = sut.registerBook("ISBN-0815", "Just a book");

    assertThat(actualCount).isEqualTo(1);
  }
}
