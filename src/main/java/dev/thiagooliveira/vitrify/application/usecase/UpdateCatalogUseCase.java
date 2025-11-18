package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.CatalogNotFoundException;
import dev.thiagooliveira.vitrify.domain.model.Catalog;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.UUID;

public class UpdateCatalogUseCase {

  private final BusinessRepository businessRepository;

  public UpdateCatalogUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Catalog execute(UUID businessId, UUID catalogId, LocalizedContent name) {
    var business =
        businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var catalog = business.getCatalog(catalogId).orElseThrow(CatalogNotFoundException::new);
    catalog.update(name);
    businessRepository.save(business);
    return catalog;
  }
}
