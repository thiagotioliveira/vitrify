package dev.thiagooliveira.vitrify.infrastructure.web.dto;

import dev.thiagooliveira.vitrify.domain.model.Business;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessModel {
  private final String colorSecondary;
  private final String colorPrimary;
  private final String backgroundColorPrimary;
  private final String backgroundColorSecondary;
  private final String name;
  private final String address;
  private final List<SocialLinkModel> socialLinks;
  private final List<String> supportedLanguages;
  private final List<CatalogModel> catalogs;

  public BusinessModel(Business business) {
    this.colorSecondary = "#3a3f42";
    this.colorPrimary = "#ffffff";
    this.backgroundColorPrimary = "#3a3f42";
    this.backgroundColorSecondary = "#f8f9fa";
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
    return socialLinks;
  }

  public String getColorSecondary() {
    return colorSecondary;
  }

  public String getColorPrimary() {
    return colorPrimary;
  }

  public String getBackgroundColorSecondary() {
    return backgroundColorSecondary;
  }

  public String getBackgroundColorPrimary() {
    return backgroundColorPrimary;
  }
}
