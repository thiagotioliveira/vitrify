package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "offering_image")
public class OfferingImageEntity {

  @EmbeddedId private OfferingImageKey id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("offeringId")
  @JoinColumn(name = "offering_id", nullable = false)
  private OfferingEntity offering;

  public OfferingImageEntity() {}

  public OfferingImageEntity(OfferingEntity offering, String name) {
    this.offering = offering;
    this.id = new OfferingImageKey(offering.getId(), name);
  }

  public OfferingEntity getOffering() {
    return offering;
  }

  public void setOffering(OfferingEntity offering) {
    this.offering = offering;
    if (id != null) {
      id.setOfferingId(offering.getId());
    }
  }

  public String getName() {
    return id.getName();
  }

  public void setName(String name) {
    id.setName(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OfferingImageEntity)) return false;
    OfferingImageEntity that = (OfferingImageEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
