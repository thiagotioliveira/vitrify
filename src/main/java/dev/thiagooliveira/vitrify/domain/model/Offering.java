package dev.thiagooliveira.vitrify.domain.model;

import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import java.math.BigDecimal;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Offering {
  private UUID id;
  private BigDecimal price;
  private LocalizedContent name;
  private LocalizedContent description;
  private List<String> images;

  private Offering(
      UUID id,
      BigDecimal price,
      LocalizedContent name,
      LocalizedContent description,
      List<String> images) {
    this.id = id;
    this.price = price;
    this.name = name;
    this.description = description;
    this.images = images != null ? new ArrayList<>(images) : new ArrayList<>();
  }

  public static Offering create(
      BigDecimal price, LocalizedContent name, LocalizedContent description, List<String> images) {
    validate(price, name, description, images);
    return new Offering(UUID.randomUUID(), price, name, description, images);
  }

  public static Offering load(
      UUID id,
      BigDecimal price,
      LocalizedContent name,
      LocalizedContent description,
      List<String> images) {
    return new Offering(id, price, name, description, images);
  }

  public void update(
      BigDecimal price, LocalizedContent name, LocalizedContent description, List<String> images) {
    validate(price, name, description, images);
    this.price = price;
    this.name = name;
    this.description = description;
    this.images = new ArrayList<>(images);
  }

  public UUID getId() {
    return id;
  }

  public LocalizedContent getName() {
    return name;
  }

  public LocalizedContent getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public List<String> getImages() {
    return Collections.unmodifiableList(images);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Offering offering = (Offering) o;
    return Objects.equals(id, offering.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  private static void validate(
      BigDecimal price, LocalizedContent name, LocalizedContent description, List<String> images) {
    validatePrice(price);
    validateLocalizedContent(name, "Offering name");
    validateLocalizedContent(description, "Offering description");
    validateImages(images);
  }

  private static void validatePrice(BigDecimal price) {
    if (price == null) throw new DomainException("Price cannot be null");
    if (price.compareTo(BigDecimal.ZERO) < 0) throw new DomainException("Price cannot be negative");
  }

  private static void validateLocalizedContent(LocalizedContent content, String fieldName) {
    if (content == null) throw new DomainException(fieldName + " cannot be null");
    content
        .getContentMap()
        .forEach(
            (language, text) -> {
              if (text == null || StringUtils.isBlank(text.content())) {
                throw new DomainException(
                    String.format("%s for language %s cannot be blank", fieldName, language));
              }
            });
    if (content.getContentMap().isEmpty()) {
      throw new DomainException(fieldName + " must have at least one language translation");
    }
  }

  private static void validateImages(List<String> images) {
    if (images == null || images.isEmpty()) {
      throw new DomainException("Offering must have at least one image");
    }
    images.forEach(
        url -> {
          if (StringUtils.isBlank(url)) {
            throw new DomainException("Offering image URL cannot be blank");
          }
        });
  }
}
