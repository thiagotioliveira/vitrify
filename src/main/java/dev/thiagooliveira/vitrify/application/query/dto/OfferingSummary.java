package dev.thiagooliveira.vitrify.application.query.dto;

import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import java.util.Objects;
import java.util.UUID;

public class OfferingSummary {
  private final UUID businessId;
  private final UUID catalogId;
  private final UUID categoryId;
  private final UUID id;
  private final LocalizedContent name;
  private final LocalizedContent description;

  public OfferingSummary(
      UUID businessId,
      UUID catalogId,
      UUID categoryId,
      UUID id,
      LocalizedContent name,
      LocalizedContent description) {
    this.businessId = businessId;
    this.catalogId = catalogId;
    this.categoryId = categoryId;
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public static OfferingSummary load(
      UUID businessId,
      UUID catalogId,
      UUID categoryId,
      UUID id,
      Language language,
      String name,
      String description) {
    return new OfferingSummary(
        businessId,
        catalogId,
        categoryId,
        id,
        LocalizedContent.of(language, name),
        LocalizedContent.of(language, description));
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

  public UUID getCategoryId() {
    return categoryId;
  }

  public LocalizedContent getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    OfferingSummary that = (OfferingSummary) o;
    return Objects.equals(businessId, that.businessId)
        && Objects.equals(catalogId, that.catalogId)
        && Objects.equals(categoryId, that.categoryId)
        && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(businessId, catalogId, categoryId, id);
  }
}
