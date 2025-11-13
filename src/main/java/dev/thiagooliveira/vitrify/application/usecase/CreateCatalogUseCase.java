package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.model.Catalog;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.UUID;

public class CreateCatalogUseCase {

  private final BusinessRepository businessRepository;

  public CreateCatalogUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Catalog execute(UUID businessId, LocalizedContent name) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var catalog = Catalog.create(name);
    business.addCatalog(catalog);
    this.businessRepository.save(business);
    return catalog;
  }
}
