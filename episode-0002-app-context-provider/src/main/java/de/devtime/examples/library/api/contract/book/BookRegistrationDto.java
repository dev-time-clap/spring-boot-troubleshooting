package de.devtime.examples.library.api.contract.book;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@Builder(setterPrefix = "with")
public class BookRegistrationDto implements Serializable {

  private static final long serialVersionUID = 2039002275615448750L;

  // Fachliche Daten
  private String isbn;
  private String title;
  private String author;

}
