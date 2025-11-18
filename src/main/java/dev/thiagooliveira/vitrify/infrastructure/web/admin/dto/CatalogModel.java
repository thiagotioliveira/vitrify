package dev.thiagooliveira.vitrify.infrastructure.web.admin.dto;

import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import dev.thiagooliveira.vitrify.domain.model.Catalog;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.model.LocalizedText;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CatalogModel {
  private UUID businessId;
  private UUID id;
  private Map<Language, String> name = new EnumMap<>(Language.class);

  public CatalogModel(UUID businessId) {
    this.businessId = businessId;
  }

  public CatalogModel() {}

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

  public String textFor(Language lang) {
    return name.getOrDefault(lang, "");
  }

  public LocalizedContent getNameLocalizedContent() {
    var texts = new EnumMap<Language, LocalizedText>(Language.class);
    name.forEach((lang, text) -> texts.put(lang, new LocalizedText(lang, text)));
    return new LocalizedContent(texts);
  }

  public String multiLangName(List<Language> supportedLanguages) {
    return supportedLanguages.stream()
        .map(lang -> lang + ": " + textFor(lang))
        .collect(Collectors.joining(" | "));
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
}
