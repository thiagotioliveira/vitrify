package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.CatalogNotFoundException;
import dev.thiagooliveira.vitrify.domain.model.Category;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.UUID;

public class CreateCategoryUseCase {

  private final BusinessRepository businessRepository;

  public CreateCategoryUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Category execute(UUID businessId, UUID catalogId, LocalizedContent name) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var catalog = business.getCatalog(catalogId).orElseThrow(CatalogNotFoundException::new);
    var category = Category.create(name);
    business.addCategory(catalogId, category);
    this.businessRepository.save(business);
    return category;
  }
}
