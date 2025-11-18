package dev.thiagooliveira.vitrify.infrastructure.persistence.projection;

import dev.thiagooliveira.vitrify.domain.model.Language;
import java.util.UUID;

public interface OfferingProjection {

  UUID getBusinessId();

  UUID getCatalogId();

  Language getCatalogLanguage();

  String getCatalogName();

  UUID getCategoryId();

  Language getCategoryLanguage();

  String getCategoryName();

  UUID getId();

  Language getLanguage();

  String getName();

  String getDescription();
}
