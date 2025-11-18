package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.CategoryNotFoundException;
import dev.thiagooliveira.vitrify.domain.model.Category;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.UUID;

public class UpdateCategoryUseCase {

  private final BusinessRepository businessRepository;

  public UpdateCategoryUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Category execute(UUID businessId, UUID catalogId, UUID categoryId, LocalizedContent name) {
    var business =
        businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var category = business.getCategory(categoryId).orElseThrow(CategoryNotFoundException::new);
    category.update(name);
    business.moveCategory(categoryId, catalogId);
    businessRepository.save(business);
    return category;
  }
}
