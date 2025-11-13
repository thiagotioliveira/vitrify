package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Language;
import jakarta.persistence.*;

@Entity
@Table(name = "category_translation")
public class CategoryTranslationEntity {

  @EmbeddedId private CategoryTranslationKey id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("categoryId")
  @JoinColumn(name = "category_id")
  private CategoryEntity category;

  @Column(nullable = false)
  private String name;

  public CategoryTranslationEntity() {}

  public CategoryTranslationEntity(CategoryEntity category, Language language, String name) {
    this.id = new CategoryTranslationKey(category.getId(), language);
    this.category = category;
    this.name = name;
  }

  public CategoryTranslationKey getId() {
    return id;
  }

  public void setId(CategoryTranslationKey id) {
    this.id = id;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
