package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "offering_image")
public class OfferingImageEntity {

  @Id @GeneratedValue private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "offering_id", nullable = false)
  private OfferingEntity offering;

  @Column(nullable = false)
  private String url;

  public OfferingImageEntity() {}

  public OfferingImageEntity(OfferingEntity offering, String url) {
    this.offering = offering;
    this.url = url;
  }

  public UUID getId() {
    return id;
  }

  public OfferingEntity getOffering() {
    return offering;
  }

  public void setOffering(OfferingEntity offering) {
    this.offering = offering;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
