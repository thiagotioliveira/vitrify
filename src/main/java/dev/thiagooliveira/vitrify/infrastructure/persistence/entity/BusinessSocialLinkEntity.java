package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.SocialLink;
import dev.thiagooliveira.vitrify.domain.model.SocialLinkType;
import jakarta.persistence.*;

@Entity
@Table(name = "business_social_link")
public class BusinessSocialLinkEntity {

  @EmbeddedId private BusinessSocialLinkKey id;

  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("businessId")
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  public BusinessSocialLinkEntity() {}

  public BusinessSocialLinkEntity(String type, String url) {
    this.id = new BusinessSocialLinkKey();
    this.id.setType(type);
    this.url = url;
  }

  public static BusinessSocialLinkEntity fromDomain(SocialLink socialLink) {
    return new BusinessSocialLinkEntity(socialLink.getType().name(), socialLink.getUrl());
  }

  public SocialLink toDomain() {
    return SocialLink.load(SocialLinkType.valueOf(this.id.getType()), this.url);
  }

  public BusinessEntity getBusiness() {
    return business;
  }

  public void setBusiness(BusinessEntity business) {
    this.business = business;
    if (this.id == null) {
      this.id = new BusinessSocialLinkKey();
    }
    this.id.setBusinessId(business.getId());
  }

  public String getType() {
    return this.id.getType();
  }

  public void setType(String type) {
    this.id.setType(type);
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
