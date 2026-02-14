package de.devtime.examples.library.businesslogic;

import org.springframework.stereotype.Service;

import de.devtime.examples.library.api.contract.publisher.PublisherDto;
import de.devtime.examples.library.api.contract.publisher.PublisherRegistrationDto;
import de.devtime.examples.library.persistence.entity.PublisherEntity;
import de.devtime.examples.library.persistence.repository.PublisherRegisteredEvent;
import de.devtime.examples.library.persistence.repository.PublisherRepository;
import de.devtime.examples.library.persistence.util.TransactionHelper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PublisherService {

  private final TransactionHelper txHelper;
  private final PublisherRepository publisherRepo;

  public void registerPublisher(final PublisherRegistrationDto registrationDto) {
    PublisherEntity entityToRegister = PublisherEntity.builder()
        .withName(registrationDto.getName())
        .buildAndInit();

    PublisherEntity savedEntity = this.txHelper.executeInTx(_ -> this.publisherRepo.save(entityToRegister));

    PublisherRegisteredEvent.builder()
        // XXX EntityToDtoMapper erstellen und hier nutzen
        .withPublisher(PublisherDto.builder()
            .withName(savedEntity.getName())
            .withId(savedEntity.getId())
            .build())
        .fire();
  }
}
