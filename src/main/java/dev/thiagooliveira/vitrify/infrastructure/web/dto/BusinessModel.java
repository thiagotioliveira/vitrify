package dev.thiagooliveira.vitrify.infrastructure.web.dto;

import dev.thiagooliveira.vitrify.domain.model.Business;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessModel {

  private final String name;
  private final String address;
  private final List<SocialLinkModel> socialLinks;
  private final List<String> supportedLanguages;
  private final List<CatalogModel> catalogs;

  public BusinessModel(Business business) {
    this.name = business.getName();
    this.address = business.getAddress().orElse(null);
    this.socialLinks =
        business.getSocialLinks().stream().map(SocialLinkModel::new).collect(Collectors.toList());
    this.supportedLanguages =
        business.getSupportedLanguages().stream().map(Enum::name).collect(Collectors.toList());
    this.catalogs =
        business.getCatalogs().stream().map(CatalogModel::new).collect(Collectors.toList());
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public List<String> getSupportedLanguages() {
    return supportedLanguages;
  }

  public List<CatalogModel> getCatalogs() {
    return catalogs;
  }

  public List<SocialLinkModel> getSocialLinks() {
    if (socialLinks == null || socialLinks.isEmpty()) {
      return Collections.emptyList();
    }

    // queremos até 3 elementos
    List<SocialLinkModel> result = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      result.add(socialLinks.get(i % socialLinks.size()));
    }
    return result;
  }
}
