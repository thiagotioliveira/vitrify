package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.model.Catalog;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.SocialLink;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "business")
public class BusinessEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true, name = "business_alias")
  private String alias;

  @Column(nullable = true)
  private String address;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "business_languages", joinColumns = @JoinColumn(name = "business_id"))
  @Column(name = "language")
  @Enumerated(EnumType.STRING)
  private Set<Language> supportedLanguages;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CatalogEntity> catalogs;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BusinessSocialLinkEntity> socialLinks;

  public BusinessEntity() {}

  public static BusinessEntity fromDomain(Business business) {
    BusinessEntity entity = new BusinessEntity();
    entity.id = business.getId();
    entity.name = business.getName();
    entity.alias = business.getAlias();
    entity.address = business.getAddress().orElse(null);
    entity.supportedLanguages = business.getSupportedLanguages();

    entity.catalogs =
        business.getCatalogs().stream().map(CatalogEntity::fromDomain).collect(Collectors.toList());

    entity.catalogs.forEach(catalog -> catalog.setBusiness(entity));

    entity.socialLinks =
        business.getSocialLinks().stream()
            .map(BusinessSocialLinkEntity::fromDomain)
            .collect(Collectors.toList());
    entity.socialLinks.forEach(link -> link.setBusiness(entity));

    return entity;
  }

  public Business toDomain() {
    Set<SocialLink> links =
        socialLinks != null
            ? socialLinks.stream()
                .map(BusinessSocialLinkEntity::toDomain)
                .collect(Collectors.toSet())
            : new HashSet<>();
    Set<Catalog> catalogs =
        this.catalogs != null
            ? this.catalogs.stream().map(CatalogEntity::toDomain).collect(Collectors.toSet())
            : new HashSet<>();

    Set<Language> languages =
        supportedLanguages != null ? new HashSet<>(supportedLanguages) : new HashSet<>();

    return Business.load(
        id, name, alias, Optional.ofNullable(this.address), links, languages, catalogs);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Set<Language> getSupportedLanguages() {
    return supportedLanguages;
  }

  public void setSupportedLanguages(Set<Language> supportedLanguages) {
    this.supportedLanguages = supportedLanguages;
  }

  public List<CatalogEntity> getCatalogs() {
    return catalogs;
  }

  public void setCatalogs(List<CatalogEntity> catalogs) {
    this.catalogs = catalogs;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<BusinessSocialLinkEntity> getSocialLinks() {
    return socialLinks;
  }

  public void setSocialLinks(List<BusinessSocialLinkEntity> socialLinks) {
    this.socialLinks = socialLinks;
  }
}
