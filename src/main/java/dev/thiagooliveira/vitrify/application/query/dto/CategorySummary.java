package dev.thiagooliveira.vitrify.application.query.dto;

import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import java.util.Objects;
import java.util.UUID;

public class CategorySummary {
  private final UUID businessId;
  private final UUID catalogId;
  private final UUID id;
  private final LocalizedContent name;

  public CategorySummary(UUID businessId, UUID catalogId, UUID id, LocalizedContent name) {
    this.businessId = businessId;
    this.catalogId = catalogId;
    this.id = id;
    this.name = name;
  }

  public static CategorySummary load(
      UUID businessId, UUID catalogId, UUID id, Language language, String name) {
    return new CategorySummary(businessId, catalogId, id, LocalizedContent.of(language, name));
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

  public UUID getCatalogId() {
    return catalogId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CategorySummary that = (CategorySummary) o;
    return Objects.equals(businessId, that.businessId)
        && Objects.equals(catalogId, that.catalogId)
        && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(businessId, catalogId, id);
  }
}
