package dev.thiagooliveira.vitrify.application.query;

import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogQuery {

  List<CatalogSummary> findAllByBusinessId(UUID businessId);

  Optional<CatalogSummary> findByIdAndBusinessId(UUID id, UUID businessId);
}
