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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString

// XXX Abstrakte Klasse erstellen für ID und Persistable<UUID> Funktionalität
@Entity
@Table(name = "Publisher")
public class PublisherEntity implements Persistable<UUID> {

  @Id
  @Column(name = "ID", updatable = false, nullable = false)
  @Include
  private UUID id;

  @Column(name = "NAME")
  private String name;

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

  public static class PublisherEntityBuilder {

    public PublisherEntity buildAndInit() {
      PublisherEntity entity = build();
      entity.id = Generators.timeBasedEpochRandomGenerator().generate();
      return entity;
    }
  }
}
