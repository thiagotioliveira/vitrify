package dev.thiagooliveira.vitrify.domain.model;

import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Category {
  private UUID id;
  private LocalizedContent name;
  private Set<Offering> offerings;

  private Category(UUID id, LocalizedContent name, Set<Offering> offerings) {
    this.id = id;
    this.name = name;
    this.offerings = offerings;
  }

  public static Category create(LocalizedContent name) {
    validate(name);
    return new Category(UUID.randomUUID(), name, new LinkedHashSet<>());
  }

  public static Category load(UUID id, LocalizedContent name, Set<Offering> offeringSet) {
    return new Category(id, name, offeringSet);
  }

  public void update(LocalizedContent name) {
    validate(name);
    this.name = name;
  }

  void addOffering(Offering offering) {
    this.offerings.add(offering);
  }

  private static void validate(LocalizedContent name) {
    if (name == null) throw new DomainException("Category name cannot be null");
    name.getContentMap()
        .forEach(
            (language, content) -> {
              if (content == null || StringUtils.isBlank(content.content())) {
                throw new DomainException(
                    String.format("Category name for language %s cannot be blank", language));
              }
            });
    if (name.getContentMap().isEmpty()) {
      throw new DomainException("Category must have at least one language translation");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return Objects.equals(id, category.id);
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

  public Set<Offering> getOfferings() {
    return Collections.unmodifiableSet(offerings);
  }
}
