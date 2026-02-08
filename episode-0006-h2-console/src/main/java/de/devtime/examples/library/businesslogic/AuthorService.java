package de.devtime.examples.library.businesslogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.examples.library.api.contract.author.AuthorDto;
import de.devtime.examples.library.api.contract.author.AuthorRegistrationDto;
import de.devtime.examples.library.persistence.entity.AuthorEntity;
import de.devtime.examples.library.persistence.repository.AuthorRegisteredEvent;
import de.devtime.examples.library.persistence.repository.AuthorRepository;
import lombok.Setter;

@Service
public class AuthorService {

  @Setter(onMethod_ = @Autowired)
  private AuthorRepository authorRepo;

  @Transactional
  public void registerAuthor(final AuthorRegistrationDto registrationDto) {
    AuthorEntity authorToRegisterEntity = AuthorEntity.builder()
        .withArtistName(registrationDto.getArtistName())
        .withFirstName(registrationDto.getFirstName())
        .withLastName(registrationDto.getLastName())
        .withBirthday(registrationDto.getBirthday())
        .buildAndInit();

    AuthorEntity saveEntity = this.authorRepo.save(authorToRegisterEntity);

    AuthorRegisteredEvent.builder()
        // XXX EntityToDtoMapper erstellen und hier nutzen
        .withAuthor(AuthorDto.builder()
            .withFirstName(saveEntity.getFirstName())
            .withLastName(saveEntity.getLastName())
            .withArtistName(saveEntity.getArtistName())
            .withBirthday(saveEntity.getBirthday())
            .withId(saveEntity.getId())
            .build())
        .fire();
  }
}
