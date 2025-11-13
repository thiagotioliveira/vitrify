package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Language;
import jakarta.persistence.*;

@Entity
@Table(name = "catalog_translation")
public class CatalogTranslationEntity {

  @EmbeddedId private CatalogTranslationKey id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("catalogId")
  @JoinColumn(name = "catalog_id")
  private CatalogEntity catalog;

  @Column(nullable = false)
  private String name;

  public CatalogTranslationEntity() {}

  public CatalogTranslationEntity(CatalogEntity catalog, Language language, String name) {
    this.id = new CatalogTranslationKey(catalog.getId(), language);
    this.catalog = catalog;
    this.name = name;
  }

  public CatalogTranslationKey getId() {
    return id;
  }

  public void setId(CatalogTranslationKey id) {
    this.id = id;
  }

  public CatalogEntity getCatalog() {
    return catalog;
  }

  public void setCatalog(CatalogEntity catalog) {
    this.catalog = catalog;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
