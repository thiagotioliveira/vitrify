package dev.thiagooliveira.vitrify.application.query;

import dev.thiagooliveira.vitrify.application.query.dto.CategorySummary;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryQuery {

  List<CategorySummary> findAllByBusinessId(UUID businessId);

  Optional<CategorySummary> findByIdAndBusinessId(UUID categoryId, UUID businessId);
}
