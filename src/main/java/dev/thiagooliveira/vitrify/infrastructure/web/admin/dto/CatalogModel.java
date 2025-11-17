package dev.thiagooliveira.vitrify.infrastructure.web.admin.dto;

import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import dev.thiagooliveira.vitrify.domain.model.Catalog;
import dev.thiagooliveira.vitrify.domain.model.Language;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class CatalogModel {
  private final UUID businessId;
  private final UUID id;
  private Map<Language, String> name = new EnumMap<>(Language.class);

  public CatalogModel(CatalogSummary catalogSummary) {
    this.businessId = catalogSummary.getBusinessId();
    this.id = catalogSummary.getId();
    if (catalogSummary.getName() != null && catalogSummary.getName().getContentMap() != null) {
      catalogSummary
          .getName()
          .getContentMap()
          .forEach((lang, localizedText) -> name.put(lang, localizedText.content()));
    }
  }

  public CatalogModel(UUID businessId, Catalog catalog) {
    this.businessId = businessId;
    this.id = catalog.getId();
    if (catalog.getName() != null && catalog.getName().getContentMap() != null) {
      catalog
          .getName()
          .getContentMap()
          .forEach((lang, localizedText) -> name.put(lang, localizedText.content()));
    }
  }

  public UUID getBusinessId() {
    return businessId;
  }

  public UUID getId() {
    return id;
  }

  public Map<Language, String> getName() {
    return name;
  }
}
