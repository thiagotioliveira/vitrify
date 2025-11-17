package dev.thiagooliveira.vitrify.application.query.usecase;

import dev.thiagooliveira.vitrify.application.query.CatalogQuery;
import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import java.util.List;
import java.util.UUID;

public class GetCatalogUseCase {

  private final CatalogQuery catalogQuery;

  public GetCatalogUseCase(CatalogQuery catalogQuery) {
    this.catalogQuery = catalogQuery;
  }

  public List<CatalogSummary> execute(UUID businessId) {
    return this.catalogQuery.findAllByBusinessId(businessId);
  }
}
