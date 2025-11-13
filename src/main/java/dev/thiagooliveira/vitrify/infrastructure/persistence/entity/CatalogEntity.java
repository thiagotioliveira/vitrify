package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.*;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "catalog")
public class CatalogEntity {

  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "business_id")
  private BusinessEntity business;

  @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CatalogTranslationEntity> translations = new HashSet<>();

  @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CategoryEntity> categories = new ArrayList<>();

  public CatalogEntity() {}

  public static CatalogEntity fromDomain(dev.thiagooliveira.vitrify.domain.model.Catalog catalog) {
    CatalogEntity entity = new CatalogEntity();
    entity.setId(catalog.getId());
    if (catalog.getName() != null) {
      catalog
          .getName()
          .getContentMap()
          .forEach(
              (lang, localizedText) ->
                  entity.addTranslation(
                      new CatalogTranslationEntity(entity, lang, localizedText.content())));
    }

    if (catalog.getCategories() != null) {
      entity.setCategories(
          catalog.getCategories().stream().map(CategoryEntity::fromDomain).toList());
      entity.getCategories().forEach(category -> category.setCatalog(entity));
    }

    return entity;
  }

  public Catalog toDomain() {
    Map<Language, LocalizedText> map =
        translations.stream()
            .collect(
                Collectors.toMap(
                    t -> t.getId().getLanguage(),
                    t -> new LocalizedText(t.getId().getLanguage(), t.getName())));
    LocalizedContent name = new LocalizedContent(map);

    Set<Category> categoryList =
        categories.stream().map(CategoryEntity::toDomain).collect(Collectors.toSet());

    return Catalog.load(id, name, categoryList);
  }

  public void addTranslation(CatalogTranslationEntity translation) {
    translations.add(translation);
    translation.setCatalog(this);
  }

  public void removeTranslation(CatalogTranslationEntity translation) {
    translations.remove(translation);
    translation.setCatalog(null);
  }

  public LocalizedContent getNameLocalizedContent() {
    Map<Language, LocalizedText> map =
        translations.stream()
            .collect(
                Collectors.toMap(
                    t -> t.getId().getLanguage(),
                    t -> new LocalizedText(t.getId().getLanguage(), t.getName())));
    return new LocalizedContent(map);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BusinessEntity getBusiness() {
    return business;
  }

  public void setBusiness(BusinessEntity business) {
    this.business = business;
  }

  public Set<CatalogTranslationEntity> getTranslations() {
    return translations;
  }

  public void setTranslations(Set<CatalogTranslationEntity> translations) {
    this.translations = translations;
  }

  public List<CategoryEntity> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryEntity> categories) {
    this.categories = categories;
  }
}
