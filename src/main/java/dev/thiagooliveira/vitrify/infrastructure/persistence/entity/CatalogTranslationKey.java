package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Language;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CatalogTranslationKey implements Serializable {

  @Column(name = "catalog_id")
  private UUID catalogId;

  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private Language language;

  public CatalogTranslationKey() {}

  public CatalogTranslationKey(UUID catalogId, Language language) {
    this.catalogId = catalogId;
    this.language = language;
  }

  public UUID getCatalogId() {
    return catalogId;
  }

  public void setCatalogId(UUID catalogId) {
    this.catalogId = catalogId;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CatalogTranslationKey that)) return false;
    return Objects.equals(catalogId, that.catalogId) && language == that.language;
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalogId, language);
  }
}
