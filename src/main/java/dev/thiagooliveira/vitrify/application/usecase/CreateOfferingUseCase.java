package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.CatalogNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.CategoryNotFoundException;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.model.Offering;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateOfferingUseCase {

  private final BusinessRepository businessRepository;

  public CreateOfferingUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Offering execute(
      UUID businessId,
      UUID catalogId,
      UUID categoryId,
      BigDecimal price,
      LocalizedContent name,
      LocalizedContent description) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var catalog = business.getCatalog(catalogId).orElseThrow(CatalogNotFoundException::new);
    var category = catalog.getCategory(categoryId).orElseThrow(CategoryNotFoundException::new);
    var offering = Offering.create(price, name, description);
    business.addOffering(categoryId, offering);
    this.businessRepository.save(business);
    return offering;
  }
}
