package de.devtime.examples.library.persistence.entity;

import java.util.UUID;

import org.springframework.data.domain.Persistable;

import com.fasterxml.uuid.Generators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "Book")
public class BookEntity implements Persistable<UUID> {

  @Id
  @Column(name = "ID", updatable = false, nullable = false)
  @Include
  private UUID id;

  @Column(name = "ISBN", nullable = false)
  private String isbn;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "AUTHOR")
  private String author;

  @Column(name = "IS_ON_LOAN")
  private boolean isOnLoan;

  @Transient
  private boolean persisted;

  @PostPersist
  @PostLoad
  private void setPersisted() {
    this.persisted = true;
  }

  @Override
  public boolean isNew() {
    return !this.persisted;
  }

  public static class BookEntityBuilder {

    public BookEntity buildAndInit() {
      BookEntity entity = build();
      entity.id = Generators.timeBasedEpochRandomGenerator().generate();
      return entity;
    }

  }
}
