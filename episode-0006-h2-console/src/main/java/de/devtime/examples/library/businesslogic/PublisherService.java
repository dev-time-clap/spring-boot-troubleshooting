package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.examples.library.api.contract.publisher.PublisherDto;
import de.devtime.examples.library.api.contract.publisher.PublisherRegistrationDto;
import de.devtime.examples.library.persistence.entity.PublisherEntity;
import de.devtime.examples.library.persistence.repository.PublisherRegisteredEvent;
import de.devtime.examples.library.persistence.repository.PublisherRepository;
import lombok.Setter;

@Service
public class PublisherService {

  @Setter(onMethod_ = @Autowired)
  private PublisherRepository publisherRepo;

  @Transactional
  public void registerPublisher(final PublisherRegistrationDto registrationDto) {
    PublisherEntity entityToRegister = PublisherEntity.builder()
        .withName(registrationDto.getName())
        .buildAndInit();

    PublisherEntity savedEntity = this.publisherRepo.save(entityToRegister);

    PublisherRegisteredEvent.builder()
        // XXX EntityToDtoMapper erstellen und hier nutzen
        .withPublisher(PublisherDto.builder()
            .withName(savedEntity.getName())
            .withId(savedEntity.getId())
            .build())
        .fire();
  }
}
