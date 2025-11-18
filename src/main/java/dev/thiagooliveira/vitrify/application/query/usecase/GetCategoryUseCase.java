package dev.thiagooliveira.vitrify.application.query.usecase;

import dev.thiagooliveira.vitrify.application.query.CategoryQuery;
import dev.thiagooliveira.vitrify.application.query.dto.CategorySummary;
import java.util.List;
import java.util.UUID;

public class GetCategoryUseCase {

  private final CategoryQuery categoryQuery;

  public GetCategoryUseCase(CategoryQuery categoryQuery) {
    this.categoryQuery = categoryQuery;
  }

  public List<CategorySummary> execute(UUID businessId) {
    return this.categoryQuery.findAllByBusinessId(businessId);
  }
}
