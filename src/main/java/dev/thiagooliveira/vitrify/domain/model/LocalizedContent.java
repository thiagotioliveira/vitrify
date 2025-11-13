package dev.thiagooliveira.vitrify.domain.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LocalizedContent {
  private final Map<Language, LocalizedText> contentMap;

  public LocalizedContent(Map<Language, LocalizedText> contentMap) {
    this.contentMap = new HashMap<>(contentMap);
  }

  public String contentFor(Language language) {
    return Optional.ofNullable(contentMap.get(language))
        .map(LocalizedText::content)
        .orElseGet(
            () ->
                contentMap
                    .getOrDefault(Language.PT, new LocalizedText(Language.PT, "-"))
                    .content());
  }

  public LocalizedContent with(Language language, String content) {
    this.contentMap.put(language, new LocalizedText(language, content));
    return this;
  }

  public static LocalizedContent of(Language language, String content) {
    return new LocalizedContent(Map.of(language, new LocalizedText(language, content)));
  }

  public Map<Language, LocalizedText> getContentMap() {
    return Collections.unmodifiableMap(contentMap);
  }
}
