package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CategoryTranslationKey implements Serializable {

  @Column(name = "category_id")
  private UUID categoryId;

  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private Language language;

  public CategoryTranslationKey() {}

  public CategoryTranslationKey(UUID categoryId, Language language) {
    this.categoryId = categoryId;
    this.language = language;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CategoryTranslationKey that = (CategoryTranslationKey) o;
    return Objects.equals(categoryId, that.categoryId) && language == that.language;
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryId, language);
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }
}
