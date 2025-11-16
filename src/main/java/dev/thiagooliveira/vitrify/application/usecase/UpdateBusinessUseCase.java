package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.Set;
import java.util.UUID;

public class UpdateBusinessUseCase {

  private final BusinessRepository businessRepository;

  public UpdateBusinessUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Business execute(
      UUID businessId, String name, String address, Set<Language> supportedLanguages) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    business.update(name, address, supportedLanguages);
    this.businessRepository.save(business);
    return business;
  }
}
