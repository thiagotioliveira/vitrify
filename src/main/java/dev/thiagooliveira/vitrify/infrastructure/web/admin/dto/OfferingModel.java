package dev.thiagooliveira.vitrify.infrastructure.web.admin.dto;

import dev.thiagooliveira.vitrify.application.query.dto.OfferingSummary;
import dev.thiagooliveira.vitrify.domain.model.Language;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class OfferingModel {
  private UUID businessId;
  private UUID catalogId;
  private Map<Language, String> catalogName = new EnumMap<>(Language.class);
  private UUID categoryId;
  private Map<Language, String> categoryName = new EnumMap<>(Language.class);
  private UUID id;
  private Map<Language, String> name = new EnumMap<>(Language.class);

  public OfferingModel() {}

  public OfferingModel(OfferingSummary offeringSummary) {
    this.businessId = offeringSummary.getBusinessId();
    this.catalogId = offeringSummary.getCatalogId();
    this.categoryId = offeringSummary.getCategoryId();
    this.id = offeringSummary.getId();
    if (offeringSummary.getName() != null && offeringSummary.getName().getContentMap() != null) {
      offeringSummary
          .getName()
          .getContentMap()
          .forEach((lang, localizedText) -> name.put(lang, localizedText.content()));
    }
    if (offeringSummary.getCatalogName() != null
        && offeringSummary.getCatalogName().getContentMap() != null) {
      offeringSummary
          .getCatalogName()
          .getContentMap()
          .forEach((lang, localizedText) -> catalogName.put(lang, localizedText.content()));
    }
    if (offeringSummary.getCategoryName() != null
        && offeringSummary.getCategoryName().getContentMap() != null) {
      offeringSummary
          .getCategoryName()
          .getContentMap()
          .forEach((lang, localizedText) -> categoryName.put(lang, localizedText.content()));
    }
  }

  public UUID getBusinessId() {
    return businessId;
  }

  public void setBusinessId(UUID businessId) {
    this.businessId = businessId;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  public Map<Language, String> getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(Map<Language, String> categoryName) {
    this.categoryName = categoryName;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Map<Language, String> getName() {
    return name;
  }

  public void setName(Map<Language, String> name) {
    this.name = name;
  }

  public UUID getCatalogId() {
    return catalogId;
  }

  public void setCatalogId(UUID catalogId) {
    this.catalogId = catalogId;
  }

  public Map<Language, String> getCatalogName() {
    return catalogName;
  }

  public void setCatalogName(Map<Language, String> catalogName) {
    this.catalogName = catalogName;
  }
}
