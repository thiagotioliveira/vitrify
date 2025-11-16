package dev.thiagooliveira.vitrify.infrastructure.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.seed")
public class AppSeedProperties {

  private Business business;

  public Business getBusiness() {
    return business;
  }

  public void setBusiness(Business business) {
    this.business = business;
  }

  public static class Business {
    private String name;
    private String alias;
    private String address;
    private List<String> supportedLanguages;
    private List<Catalog> catalogs;

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

    public List<String> getSupportedLanguages() {
      return supportedLanguages;
    }

    public void setSupportedLanguages(List<String> supportedLanguages) {
      this.supportedLanguages = supportedLanguages;
    }

    public List<Catalog> getCatalogs() {
      return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
      this.catalogs = catalogs;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }
  }

  public static class Catalog {
    private Map<String, String> name; // PT -> EN
    private List<Category> categories;

    public Map<String, String> getName() {
      return name;
    }

    public void setName(Map<String, String> name) {
      this.name = name;
    }

    public List<Category> getCategories() {
      return categories;
    }

    public void setCategories(List<Category> categories) {
      this.categories = categories;
    }
  }

  public static class Category {
    private Map<String, String> name;
    private List<Offering> offerings;

    public Map<String, String> getName() {
      return name;
    }

    public void setName(Map<String, String> name) {
      this.name = name;
    }

    public List<Offering> getOfferings() {
      return offerings;
    }

    public void setOfferings(List<Offering> offerings) {
      this.offerings = offerings;
    }
  }

  public static class Offering {
    private Map<String, String> name;
    private Map<String, String> description;
    private BigDecimal price;
    private List<String> images;

    public Map<String, String> getName() {
      return name;
    }

    public void setName(Map<String, String> name) {
      this.name = name;
    }

    public Map<String, String> getDescription() {
      return description;
    }

    public void setDescription(Map<String, String> description) {
      this.description = description;
    }

    public BigDecimal getPrice() {
      return price;
    }

    public void setPrice(BigDecimal price) {
      this.price = price;
    }

    public List<String> getImages() {
      return images;
    }

    public void setImages(List<String> images) {
      this.images = images;
    }
  }
}
