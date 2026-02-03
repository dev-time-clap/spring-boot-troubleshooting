package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SetterService {

  @Setter(onMethod_ = @Autowired, value = AccessLevel.PACKAGE)
  private BookCache bookCache;

  private StatisticService statisticService;

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

  @Autowired
  void setStatisticService(final StatisticService statisticService) {
    this.statisticService = statisticService;
  }
}
