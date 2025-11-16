package dev.thiagooliveira.vitrify.infrastructure.web.dto;

import static dev.thiagooliveira.vitrify.domain.model.Constants.CURRENCY;
import static dev.thiagooliveira.vitrify.domain.model.Constants.PREFIX_IMAGE_URL;

import dev.thiagooliveira.vitrify.domain.model.Offering;
import java.util.*;
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
        offering.getImages().stream()
            .map(s -> String.format(PREFIX_IMAGE_URL, s))
            .collect(Collectors.toList());
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
    if (images == null || images.isEmpty()) {
      return Collections.emptyList();
    }

    List<String> result = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      result.add(images.get(i % images.size()));
    }
    return result;
  }
}
