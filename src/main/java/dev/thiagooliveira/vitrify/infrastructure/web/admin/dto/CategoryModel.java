package dev.thiagooliveira.vitrify.infrastructure.web.admin.dto;

import dev.thiagooliveira.vitrify.application.query.dto.CategorySummary;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.model.LocalizedText;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class CategoryModel {
  private UUID businessId;
  private UUID catalogId;
  private Map<Language, String> catalogName = new EnumMap<>(Language.class);
  private UUID id;
  private Map<Language, String> name = new EnumMap<>(Language.class);

  public CategoryModel() {}

  public CategoryModel(CategorySummary categorySummary) {
    this.businessId = categorySummary.getBusinessId();
    this.catalogId = categorySummary.getCatalogId();
    this.id = categorySummary.getId();
    if (categorySummary.getName() != null && categorySummary.getName().getContentMap() != null) {
      categorySummary
          .getName()
          .getContentMap()
          .forEach((lang, localizedText) -> name.put(lang, localizedText.content()));
    }
    if (categorySummary.getCatalogName() != null
        && categorySummary.getCatalogName().getContentMap() != null) {
      categorySummary
          .getCatalogName()
          .getContentMap()
          .forEach((lang, localizedText) -> catalogName.put(lang, localizedText.content()));
    }
  }

  public String textFor(Language lang) {
    return name.getOrDefault(lang, "");
  }

  public LocalizedContent getNameLocalizedContent() {
    var texts = new EnumMap<Language, LocalizedText>(Language.class);
    name.forEach((lang, text) -> texts.put(lang, new LocalizedText(lang, text)));
    return new LocalizedContent(texts);
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

  public void setBusinessId(UUID businessId) {
    this.businessId = businessId;
  }

  public void setId(UUID id) {
    this.id = id;
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
