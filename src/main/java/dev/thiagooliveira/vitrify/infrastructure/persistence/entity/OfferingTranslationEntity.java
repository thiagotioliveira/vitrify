package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Language;
import jakarta.persistence.*;

@Entity
@Table(name = "offering_translation")
public class OfferingTranslationEntity {

  @EmbeddedId private OfferingTranslationKey id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("offeringId")
  @JoinColumn(name = "offering_id")
  private OfferingEntity offering;

  @Column(nullable = false)
  private String name;

  @Column private String description;

  public OfferingTranslationEntity() {}

  public OfferingTranslationEntity(
      OfferingEntity offering, Language language, String name, String description) {
    this.id = new OfferingTranslationKey(offering.getId(), language);
    this.offering = offering;
    this.name = name;
    this.description = description;
  }

  public OfferingTranslationKey getId() {
    return id;
  }

  public void setId(OfferingTranslationKey id) {
    this.id = id;
  }

  public OfferingEntity getOffering() {
    return offering;
  }

  public void setOffering(OfferingEntity offering) {
    this.offering = offering;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
