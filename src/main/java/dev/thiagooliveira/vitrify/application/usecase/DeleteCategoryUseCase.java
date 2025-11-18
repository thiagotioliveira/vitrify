package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.UUID;

public class DeleteCategoryUseCase {

  private final BusinessRepository businessRepository;

  public DeleteCategoryUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public void execute(UUID businessId, UUID categoryId) {
    var business = businessRepository.findById(businessId).orElseThrow();
    business.removeCategory(categoryId);
    businessRepository.save(business);
  }
}
