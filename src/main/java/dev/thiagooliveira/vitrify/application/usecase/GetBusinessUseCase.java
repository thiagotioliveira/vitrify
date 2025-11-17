package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.Optional;
import java.util.UUID;

public class GetBusinessUseCase {

  private final BusinessRepository businessRepository;

  public GetBusinessUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Optional<Business> execute(UUID businessId) {
    return businessRepository.findById(businessId);
  }

  public Optional<Business> execute(String alias) {
    return businessRepository.findByAlias(alias);
  }
}
