package de.devtime.examples.library.businesslogic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class BookCache {

  private final Map<String, String> books = new HashMap<>();

  public void putBook(final String isbn, final String name) {
    this.books.put(isbn, name);
  }

  public String getBook(final String isbn) {
    return this.books.get(isbn);
  }
}
