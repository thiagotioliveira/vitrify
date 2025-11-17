package dev.thiagooliveira.vitrify.application.query;

import dev.thiagooliveira.vitrify.application.query.dto.BusinessSummary;
import java.util.Optional;
import java.util.UUID;

public interface BusinessQuery {

  Optional<BusinessSummary> findById(UUID id);
}
