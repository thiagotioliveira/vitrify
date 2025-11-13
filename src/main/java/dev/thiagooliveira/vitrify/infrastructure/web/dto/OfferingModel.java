package dev.thiagooliveira.vitrify.infrastructure.web.dto;

import static dev.thiagooliveira.vitrify.domain.model.Constants.CURRENCY;

import dev.thiagooliveira.vitrify.domain.model.Offering;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OfferingModel {
  private final UUID id;
  private final Map<String, String> name;
  private final Map<String, String> description;
  private final String price;
  private final List<String> images;

  public OfferingModel(Offering offering) {
    this.id = offering.getId();
    this.name =
        offering.getName().getContentMap().entrySet().stream()
            .collect(
                Collectors.toMap(
                    entry -> entry.getKey().toString(), entry -> entry.getValue().content()));
    this.description =
        offering.getDescription().getContentMap().entrySet().stream()
            .collect(
                Collectors.toMap(
                    entry -> entry.getKey().toString(), entry -> entry.getValue().content()));
    this.price = offering.getPrice().toString() + CURRENCY;
    this.images =
        List.of(
            "https://placehold.co/800x400?text=800x400",
            "https://placehold.co/800x400?text=800x400");
  }

  public UUID getId() {
    return id;
  }

  public Map<String, String> getName() {
    return name;
  }

  public Map<String, String> getDescription() {
    return description;
  }

  public String getPrice() {
    return price;
  }

  public List<String> getImages() {
    return images;
  }
}
