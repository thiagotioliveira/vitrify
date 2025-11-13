package dev.thiagooliveira.vitrify.domain.model;

import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class Offering {
  private UUID id;
  private BigDecimal price;
  private LocalizedContent name;
  private LocalizedContent description;

  private Offering(UUID id, BigDecimal price, LocalizedContent name, LocalizedContent description) {
    this.id = id;
    this.price = price;
    this.name = name;
    this.description = description;
  }

  public static Offering create(
      BigDecimal price, LocalizedContent name, LocalizedContent description) {
    validate(price, name, description);
    return new Offering(UUID.randomUUID(), price, name, description);
  }

  public static Offering load(
      UUID id, BigDecimal price, LocalizedContent name, LocalizedContent description) {
    return new Offering(id, price, name, description);
  }

  public void update(BigDecimal price, LocalizedContent name, LocalizedContent description) {
    validate(price, name, description);
    this.price = price;
    this.name = name;
    this.description = description;
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

  private static void validate(
      BigDecimal price, LocalizedContent name, LocalizedContent description) {
    validatePrice(price);
    validateLocalizedContent(name, "Offering name");
    validateLocalizedContent(description, "Offering description");
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
}
