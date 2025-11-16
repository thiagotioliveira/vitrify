package dev.thiagooliveira.vitrify.infrastructure.persistence.entity;

import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.model.Catalog;
import dev.thiagooliveira.vitrify.domain.model.Language;
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
    return entity;
  }

  public Business toDomain() {
    Set<Catalog> domainCatalogs =
        catalogs != null
            ? catalogs.stream().map(CatalogEntity::toDomain).collect(Collectors.toSet())
            : new HashSet<>();

    Set<Language> languages =
        supportedLanguages != null ? new HashSet<>(supportedLanguages) : new HashSet<>();

    return Business.load(
        id, name, alias, Optional.ofNullable(this.address), languages, domainCatalogs);
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
}
