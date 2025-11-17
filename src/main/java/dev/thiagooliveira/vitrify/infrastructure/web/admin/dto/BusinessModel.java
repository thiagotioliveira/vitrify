package dev.thiagooliveira.vitrify.infrastructure.web.admin.dto;

import dev.thiagooliveira.vitrify.application.query.dto.BusinessSummary;
import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.model.Language;
import java.util.Set;
import java.util.UUID;

public class BusinessModel {

  private final UUID id;
  private final String name;
  private final String address;
  private final Set<Language> supportedLanguages;

  public BusinessModel(BusinessSummary businessSummary) {
    this.id = businessSummary.getId();
    this.name = businessSummary.getName();
    this.address = businessSummary.getAddress();
    this.supportedLanguages = businessSummary.getSupportedLanguages();
  }

  public BusinessModel(Business business) {
    this.id = business.getId();
    this.name = business.getName();
    this.address = business.getAddress().orElse(null);
    this.supportedLanguages = business.getSupportedLanguages();
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
