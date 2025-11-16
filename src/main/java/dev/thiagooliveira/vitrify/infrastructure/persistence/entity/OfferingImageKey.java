package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OfferingImageKey implements Serializable {

  @Column(name = "offering_id")
  private UUID offeringId;

  private String name;

  public OfferingImageKey() {}

  public OfferingImageKey(UUID offeringId, String name) {
    this.offeringId = offeringId;
    this.name = name;
  }

  public UUID getOfferingId() {
    return offeringId;
  }

  public void setOfferingId(UUID offeringId) {
    this.offeringId = offeringId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OfferingImageKey)) return false;
    OfferingImageKey that = (OfferingImageKey) o;
    return Objects.equals(offeringId, that.offeringId) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offeringId, name);
  }
}
