package dev.thiagooliveira.vitrify.infrastructure.persistence.projection;

import dev.thiagooliveira.vitrify.domain.model.Language;
import java.util.UUID;

public interface CategoryProjection {

  UUID getBusinessId();

  UUID getCatalogId();

  UUID getId();

  Language getLanguage();

  String getName();
}
