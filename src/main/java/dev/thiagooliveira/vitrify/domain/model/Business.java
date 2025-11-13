package dev.thiagooliveira.vitrify.domain.model;

import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import java.text.Normalizer;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Business {
  private UUID id;
  private String name;
  private String alias;
  private Set<Language> supportedLanguages;
  private Set<Catalog> catalogs;

  private Business(
      UUID id, String name, String alias, Set<Language> supportedLanguages, Set<Catalog> catalogs) {
    this.id = id;
    this.name = name;
    this.alias = alias;
    this.supportedLanguages = supportedLanguages;
    this.catalogs = catalogs;
  }

  public static Business load(
      UUID id, String name, String alias, Set<Language> supportedLanguages, Set<Catalog> catalogs) {
    return new Business(id, name, alias, supportedLanguages, catalogs);
  }

  public static Business create(String name, Set<Language> supportedLanguages) {
    validate(name, supportedLanguages);
    return new Business(
        UUID.randomUUID(),
        name,
        generateAlias(name),
        new HashSet<>(supportedLanguages),
        new LinkedHashSet<>());
  }

  public void update(String name, Set<Language> supportedLanguages) {
    validate(name, supportedLanguages);
    this.name = name;
    this.supportedLanguages.clear();
    this.supportedLanguages.addAll(supportedLanguages);
  }

  public void addCatalog(Catalog catalog) {
    if (catalog == null) {
      throw new DomainException("Catalog cannot be null");
    }

    for (Language language : supportedLanguages) {
      if (!catalog.getName().getContentMap().containsKey(language)) {
        throw new DomainException(
            "Catalog name must have content for all supported languages. Missing: " + language);
      }
    }

    this.catalogs.add(catalog);
  }

  public void addCategory(UUID catalogId, Category category) {
    if (category == null) {
      throw new DomainException("Category cannot be null");
    }

    for (Language language : supportedLanguages) {
      if (!category.getName().getContentMap().containsKey(language)) {
        throw new DomainException(
            "Category name must have content for all supported languages. Missing: " + language);
      }
    }

    var catalog =
        this.catalogs.stream()
            .filter(c -> c.getId().equals(catalogId))
            .findFirst()
            .orElseThrow(() -> new DomainException("Catalog id not found: " + catalogId));
    catalog.addCategory(category);
  }

  public void addOffering(UUID categoryId, Offering offering) {
    if (offering == null) {
      throw new DomainException("Offering cannot be null");
    }

    for (Language language : supportedLanguages) {
      if (!offering.getName().getContentMap().containsKey(language)) {
        throw new DomainException(
            "Offering name must have content for all supported languages. Missing: " + language);
      }
      if (!offering.getDescription().getContentMap().containsKey(language)) {
        throw new DomainException(
            "Offering description must have content for all supported languages. Missing: "
                + language);
      }
    }

    var category =
        this.catalogs.stream()
            .flatMap(catalog -> catalog.getCategories().stream())
            .filter(cat -> cat.getId().equals(categoryId))
            .findFirst()
            .orElseThrow(() -> new DomainException("Category id not found: " + categoryId));
    category.addOffering(offering);
  }

  public Optional<Catalog> getCatalog(UUID catalogId) {
    return this.catalogs.stream().filter(c -> c.getId().equals(catalogId)).findFirst();
  }

  private static void validate(String name, Set<Language> supportedLanguages) {
    // Validate name
    if (name == null) {
      throw new DomainException("Business name cannot be null");
    }
    if (StringUtils.isBlank(name)) {
      throw new DomainException("Business name cannot be blank");
    }
    if (name.length() > 255) {
      throw new DomainException("Business name cannot exceed 255 characters");
    }

    // Validate supported languages
    if (supportedLanguages == null) {
      throw new DomainException("Supported languages cannot be null");
    }
    if (supportedLanguages.isEmpty()) {
      throw new DomainException("Business must have at least one supported language");
    }
  }

  private static String generateAlias(String name) {
    if (name == null) return "";
    // Remove accents
    String normalized = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    // Remove special characters and replace spaces with hyphens
    return normalized.replaceAll("[^\\w\\s-]", "").trim().replaceAll("\\s+", "-").toLowerCase();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAlias() {
    return alias;
  }

  public Set<Language> getSupportedLanguages() {
    return Collections.unmodifiableSet(supportedLanguages);
  }

  public Set<Catalog> getCatalogs() {
    return Collections.unmodifiableSet(catalogs);
  }
}
