package dev.thiagooliveira.vitrify.application.query.usecase;

import dev.thiagooliveira.vitrify.application.query.BusinessQuery;
import dev.thiagooliveira.vitrify.application.query.dto.BusinessSummary;
import java.util.Optional;
import java.util.UUID;

public class GetBusinessUseCase {

  private final BusinessQuery businessQuery;

  public GetBusinessUseCase(BusinessQuery businessQuery) {
    this.businessQuery = businessQuery;
  }

  public Optional<BusinessSummary> execute(UUID id) {
    return this.businessQuery.findById(id);
  }
}
