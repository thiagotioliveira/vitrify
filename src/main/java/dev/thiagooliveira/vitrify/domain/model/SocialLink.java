package dev.thiagooliveira.vitrify.domain.model;

public class SocialLink {
  private SocialLinkType type;
  private String url;

  private SocialLink(SocialLinkType type, String url) {
    this.type = type;
    this.url = url;
  }

  public static SocialLink create(SocialLinkType type, String url) {
    return new SocialLink(type, url);
  }

  public static SocialLink load(SocialLinkType type, String url) {
    return new SocialLink(type, url);
  }

  public SocialLinkType getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }
}
