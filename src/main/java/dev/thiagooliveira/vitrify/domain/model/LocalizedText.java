package dev.thiagooliveira.vitrify.domain.model;

public record LocalizedText(Language language, String content) {
  public static LocalizedText of(Language language, String content) {
    return new LocalizedText(language, content);
  }
}
