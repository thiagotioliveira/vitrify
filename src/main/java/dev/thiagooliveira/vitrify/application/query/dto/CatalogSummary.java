package dev.thiagooliveira.vitrify.application.query.dto;

import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import java.util.Objects;
import java.util.UUID;

public class CatalogSummary {
  private final UUID businessId;
  private final UUID id;
  private final LocalizedContent name;

  public CatalogSummary(UUID businessId, UUID id, LocalizedContent name) {
    this.businessId = businessId;
    this.id = id;
    this.name = name;
  }

  public static CatalogSummary load(UUID businessId, UUID id, Language language, String name) {
    return new CatalogSummary(businessId, id, LocalizedContent.of(language, name));
  }

  public UUID getBusinessId() {
    return businessId;
  }

  public UUID getId() {
    return id;
  }

  public LocalizedContent getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CatalogSummary that = (CatalogSummary) o;
    return Objects.equals(businessId, that.businessId) && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(businessId, id);
  }
}
