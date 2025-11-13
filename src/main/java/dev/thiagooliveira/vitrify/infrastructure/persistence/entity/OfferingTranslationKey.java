package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OfferingTranslationKey implements Serializable {

  @Column(name = "offering_id")
  private UUID offeringId;

  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private Language language;

  public OfferingTranslationKey() {}

  public OfferingTranslationKey(UUID offeringId, Language language) {
    this.offeringId = offeringId;
    this.language = language;
  }

  public UUID getOfferingId() {
    return offeringId;
  }

  public void setOfferingId(UUID offeringId) {
    this.offeringId = offeringId;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OfferingTranslationKey that)) return false;
    return Objects.equals(offeringId, that.offeringId) && language == that.language;
  }

  @Override
  public int hashCode() {
    return Objects.hash(offeringId, language);
  }
}
