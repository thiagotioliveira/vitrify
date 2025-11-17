package dev.thiagooliveira.vitrify.application.query.dto;

import dev.thiagooliveira.vitrify.domain.model.Language;
import java.util.Set;
import java.util.UUID;

public class BusinessSummary {

  private final UUID id;
  private final String name;
  private final String address;
  private final Set<Language> supportedLanguages;

  public BusinessSummary(UUID id, String name, String address, Set<Language> supportedLanguages) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.supportedLanguages = supportedLanguages;
  }

  public static BusinessSummary load(
      UUID id, String name, String address, Set<Language> supportedLanguages) {
    return new BusinessSummary(id, name, address, supportedLanguages);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public Set<Language> getSupportedLanguages() {
    return supportedLanguages;
  }
}
