package de.devtime.examples.library.businesslogic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StatisticServiceTest {

  @Test
  void increaseRegisteredBookCountShouldUpdateTheCountByOne() {
    StatisticService sut = new StatisticService();
    assertThat(sut.getRegisteredBookCount()).isZero();

    sut.increaseRegisteredBookCount();

    assertThat(sut.getRegisteredBookCount()).isEqualTo(1);
  }
}
