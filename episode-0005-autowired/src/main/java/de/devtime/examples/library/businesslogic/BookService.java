package de.devtime.examples.library.businesslogic;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

  private final BookCache bookCache;
  private final StatisticService statisticService;

  public int registerBook(final String isbn, final String title) {
    this.bookCache.putBook(isbn, title);
    this.statisticService.increaseRegisteredBookCount();
    int currentCount = this.statisticService.getRegisteredBookCount();
    log.info("Book '{} ({})' was registered. There are {} books registered now.", title, isbn, currentCount);
    return currentCount;
  }

  public String getBook(final String isbn) {
    return this.bookCache.getBook(isbn);
  }
}
