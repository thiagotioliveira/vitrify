package dev.thiagooliveira.vitrify.infrastructure.web.dto;

import dev.thiagooliveira.vitrify.domain.model.SocialLink;

public class SocialLinkModel {

  private final String type;
  private final String url;

  public SocialLinkModel(String type, String url) {
    this.type = type;
    this.url = url;
  }

  public SocialLinkModel(SocialLink link) {
    this(link.getType().name().toLowerCase(), link.getUrl());
  }

  public String getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }
}
