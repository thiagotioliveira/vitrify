package dev.thiagooliveira.vitrify.application.query;

import dev.thiagooliveira.vitrify.application.query.dto.CategorySummary;
import java.util.List;
import java.util.UUID;

public interface CategoryQuery {

  List<CategorySummary> findAllByBusinessIdAndCatalogId(UUID businessId, UUID catalogId);
}
