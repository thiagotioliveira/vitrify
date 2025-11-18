package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.UUID;

public class DeleteCatalogUseCase {

  private final BusinessRepository businessRepository;

  public DeleteCatalogUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public void execute(UUID businessId, UUID catalogId) {
    var business =
        businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    business.removeCatalog(catalogId);
    businessRepository.save(business);
  }
}
