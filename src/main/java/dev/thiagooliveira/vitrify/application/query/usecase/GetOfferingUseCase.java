package dev.thiagooliveira.vitrify.application.query.usecase;

import dev.thiagooliveira.vitrify.application.query.OfferingQuery;
import dev.thiagooliveira.vitrify.application.query.dto.OfferingSummary;
import java.util.List;
import java.util.UUID;

public class GetOfferingUseCase {

  private final OfferingQuery offeringQuery;

  public GetOfferingUseCase(OfferingQuery offeringQuery) {
    this.offeringQuery = offeringQuery;
  }

  public List<OfferingSummary> execute(UUID businessId, UUID catalogId, UUID categoryId) {
    return this.offeringQuery.findAllByBusinessIdAndCatalogIdAndCategoryId(
        businessId, catalogId, categoryId);
  }
}
