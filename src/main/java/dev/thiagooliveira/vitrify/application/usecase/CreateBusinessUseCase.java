package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.Set;

public class CreateBusinessUseCase {

  private final BusinessRepository businessRepository;

  public CreateBusinessUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Business execute(String name, Set<Language> supportedLanguages) {
    var business = Business.create(name, supportedLanguages);
    this.businessRepository.save(business);
    return business;
  }
}
