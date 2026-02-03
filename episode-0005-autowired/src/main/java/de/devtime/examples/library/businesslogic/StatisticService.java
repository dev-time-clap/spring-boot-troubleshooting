package de.devtime.examples.library.businesslogic;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticService {

  private int registeredBookCount;

  public void increaseRegisteredBookCount() {
    this.registeredBookCount++;
  }

  public int getRegisteredBookCount() {
    return this.registeredBookCount;
  }
}
