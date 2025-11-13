package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Category;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.model.LocalizedText;
import dev.thiagooliveira.vitrify.domain.model.Offering;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "category")
public class CategoryEntity {

  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "catalog_id")
  private CatalogEntity catalog;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CategoryTranslationEntity> translations = new HashSet<>();

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OfferingEntity> offerings = new ArrayList<>();

  public CategoryEntity() {}

  public CategoryEntity(
      UUID id, CatalogEntity catalog, Set<CategoryTranslationEntity> translations) {
    this.id = id;
    this.catalog = catalog;
    this.translations = translations;
  }

  public static CategoryEntity fromDomain(Category category) {
    CategoryEntity entity = new CategoryEntity();
    entity.id = category.getId();

    if (category.getName() != null) {
      category
          .getName()
          .getContentMap()
          .forEach(
              (lang, localizedText) ->
                  entity.addTranslation(
                      new CategoryTranslationEntity(entity, lang, localizedText.content())));
    }

    if (category.getOfferings() != null) {
      entity.offerings =
          category.getOfferings().stream()
              .map(OfferingEntity::fromDomain)
              .collect(Collectors.toList());
      entity.offerings.forEach(offering -> offering.setCategory(entity));
    }

    return entity;
  }

  public Category toDomain() {
    Map<Language, LocalizedText> map =
        translations.stream()
            .collect(
                Collectors.toMap(
                    t -> t.getId().getLanguage(),
                    t -> new LocalizedText(t.getId().getLanguage(), t.getName())));
    LocalizedContent name = new LocalizedContent(map);

    List<Offering> offeringSet =
        offerings != null
            ? offerings.stream().map(OfferingEntity::toDomain).collect(Collectors.toList())
            : new ArrayList<>();

    return Category.load(id, name, offeringSet);
  }

  // ======= Traduções =======
  public void addTranslation(CategoryTranslationEntity translation) {
    translations.add(translation);
    translation.setCategory(this);
  }

  public void removeTranslation(CategoryTranslationEntity translation) {
    translations.remove(translation);
    translation.setCategory(null);
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

  public CatalogEntity getCatalog() {
    return catalog;
  }

  public void setCatalog(CatalogEntity catalog) {
    this.catalog = catalog;
  }

  public Set<CategoryTranslationEntity> getTranslations() {
    return translations;
  }

  public void setTranslations(Set<CategoryTranslationEntity> translations) {
    this.translations = translations;
  }

  public List<OfferingEntity> getOfferings() {
    return offerings;
  }

  public void setOfferings(List<OfferingEntity> offerings) {
    this.offerings = offerings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CategoryEntity that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
