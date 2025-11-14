package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.model.LocalizedText;
import dev.thiagooliveira.vitrify.domain.model.Offering;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "offering")
public class OfferingEntity {
  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private CategoryEntity category;

  private BigDecimal price;

  @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OfferingTranslationEntity> translations = new ArrayList<>();

  @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OfferingImageEntity> images = new ArrayList<>();

  public OfferingEntity() {}

  public OfferingEntity(UUID id, CategoryEntity category, BigDecimal price) {
    this.id = id;
    this.category = category;
    this.price = price;
  }

  public static OfferingEntity fromDomain(Offering offering) {
    OfferingEntity entity = new OfferingEntity();
    entity.id = offering.getId();
    entity.price = offering.getPrice();

    offering
        .getImages()
        .forEach(
            imageUrl -> {
              OfferingImageEntity imageEntity = new OfferingImageEntity(entity, imageUrl);
              entity.getImages().add(imageEntity);
            });

    if (offering.getName() != null) {
      offering
          .getName()
          .getContentMap()
          .forEach(
              (lang, localizedText) -> {
                String description = null;
                if (offering.getDescription() != null
                    && offering.getDescription().getContentMap().containsKey(lang)) {
                  description = offering.getDescription().getContentMap().get(lang).content();
                }
                entity.addTranslation(
                    new OfferingTranslationEntity(
                        entity, lang, localizedText.content(), description));
              });
    }

    return entity;
  }

  public Offering toDomain() {
    Map<Language, LocalizedText> nameMap =
        translations.stream()
            .collect(
                Collectors.toMap(
                    t -> t.getId().getLanguage(),
                    t -> new LocalizedText(t.getId().getLanguage(), t.getName())));
    LocalizedContent name = new LocalizedContent(nameMap);

    Map<Language, LocalizedText> descriptionMap =
        translations.stream()
            .filter(t -> t.getDescription() != null)
            .collect(
                Collectors.toMap(
                    t -> t.getId().getLanguage(),
                    t -> new LocalizedText(t.getId().getLanguage(), t.getDescription())));
    LocalizedContent description = new LocalizedContent(descriptionMap);

    return Offering.load(
        id,
        price,
        name,
        description,
        this.images.stream().map(OfferingImageEntity::getUrl).collect(Collectors.toList()));
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public List<OfferingImageEntity> getImages() {
    return images;
  }

  public void setImages(List<OfferingImageEntity> images) {
    this.images = images;
  }

  public List<OfferingTranslationEntity> getTranslations() {
    return translations;
  }

  public void setTranslations(List<OfferingTranslationEntity> translations) {
    this.translations.clear();
    if (translations != null) {
      this.translations.addAll(translations);
      this.translations.forEach(t -> t.setOffering(this));
    }
  }

  public void addTranslation(OfferingTranslationEntity translation) {
    translation.setOffering(this);
    this.translations.add(translation);
  }

  public void removeTranslation(OfferingTranslationEntity translation) {
    this.translations.remove(translation);
    translation.setOffering(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OfferingEntity that = (OfferingEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
