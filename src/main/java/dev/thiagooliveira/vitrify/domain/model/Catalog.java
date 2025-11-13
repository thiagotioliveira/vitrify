package dev.thiagooliveira.vitrify.domain.model;

import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Catalog {
  private UUID id;
  private LocalizedContent name;
  private List<Category> categories;

  private Catalog(UUID id, LocalizedContent name, List<Category> categories) {
    this.id = id;
    this.name = name;
    this.categories = categories;
  }

  public static Catalog load(UUID id, LocalizedContent name, List<Category> categories) {
    return new Catalog(id, name, categories);
  }

  public static Catalog create(LocalizedContent name) {
    validate(name);
    return new Catalog(UUID.randomUUID(), name, new ArrayList<>());
  }

  public void update(LocalizedContent name) {
    validate(name);
    this.name = name;
  }

  void addCategory(Category category) {
    this.categories.add(category);
  }

  public Optional<Category> getCategory(UUID categoryId) {
    return this.categories.stream().filter(c -> c.getId().equals(categoryId)).findFirst();
  }

  private static void validate(LocalizedContent name) {
    if (name == null) throw new DomainException("Catalog name cannot be null");
    name.getContentMap()
        .forEach(
            (language, content) -> {
              if (content == null || StringUtils.isBlank(content.content())) {
                throw new DomainException(
                    String.format("Catalog name for language %s cannot be blank", language));
              }
            });
    if (name.getContentMap().isEmpty()) {
      throw new DomainException("Catalog must have at least one language translation");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Catalog catalog = (Catalog) o;
    return Objects.equals(id, catalog.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  public UUID getId() {
    return id;
  }

  public LocalizedContent getName() {
    return name;
  }

  public List<Category> getCategories() {
    return Collections.unmodifiableList(categories);
  }
}
