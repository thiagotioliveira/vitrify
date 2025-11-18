package dev.thiagooliveira.vitrify.application.query.dto;

import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import java.util.Objects;
import java.util.UUID;

public class OfferingSummary {
  private final UUID businessId;
  private final UUID catalogId;
  private final LocalizedContent catalogName;
  private final UUID categoryId;
  private final LocalizedContent categoryName;
  private final UUID id;
  private final LocalizedContent name;
  private final LocalizedContent description;

  public OfferingSummary(
      UUID businessId,
      UUID catalogId,
      LocalizedContent catalogName,
      UUID categoryId,
      LocalizedContent categoryName,
      UUID id,
      LocalizedContent name,
      LocalizedContent description) {
    this.businessId = businessId;
    this.catalogId = catalogId;
    this.catalogName = catalogName;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public static OfferingSummary load(
      UUID businessId,
      UUID catalogId,
      Language language1,
      String catalogName,
      UUID categoryId,
      Language language2,
      String categoryName,
      UUID id,
      Language language,
      String name,
      String description) {
    return new OfferingSummary(
        businessId,
        catalogId,
        LocalizedContent.of(language1, catalogName),
        categoryId,
        LocalizedContent.of(language2, categoryName),
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

  public LocalizedContent getCategoryName() {
    return categoryName;
  }

  public LocalizedContent getCatalogName() {
    return catalogName;
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
