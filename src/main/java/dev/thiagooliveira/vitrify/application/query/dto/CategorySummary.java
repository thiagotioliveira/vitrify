package dev.thiagooliveira.vitrify.application.query.dto;

import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import java.util.Objects;
import java.util.UUID;

public class CategorySummary {
  private final UUID businessId;
  private final UUID catalogId;
  private final LocalizedContent catalogName;
  private final UUID id;
  private final LocalizedContent name;

  public CategorySummary(
      UUID businessId,
      UUID catalogId,
      LocalizedContent catalogName,
      UUID id,
      LocalizedContent name) {
    this.businessId = businessId;
    this.catalogId = catalogId;
    this.catalogName = catalogName;
    this.id = id;
    this.name = name;
  }

  public static CategorySummary load(
      UUID businessId,
      UUID catalogId,
      Language language1,
      String catalogName,
      UUID id,
      Language language,
      String name) {
    return new CategorySummary(
        businessId,
        catalogId,
        LocalizedContent.of(language1, catalogName),
        id,
        LocalizedContent.of(language, name));
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

  public LocalizedContent getCatalogName() {
    return catalogName;
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
