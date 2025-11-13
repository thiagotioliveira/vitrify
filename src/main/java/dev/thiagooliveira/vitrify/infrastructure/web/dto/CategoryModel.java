package dev.thiagooliveira.vitrify.infrastructure.web.dto;

import dev.thiagooliveira.vitrify.domain.model.Category;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CategoryModel {

  private final UUID id;
  private final Map<String, String> name;
  private final List<OfferingModel> offerings;

  public CategoryModel(Category category) {
    this.id = category.getId();
    this.name =
        category.getName().getContentMap().entrySet().stream()
            .collect(
                Collectors.toMap(
                    entry -> entry.getKey().toString(), entry -> entry.getValue().content()));
    this.offerings =
        category.getOfferings().stream().map(OfferingModel::new).collect(Collectors.toList());
  }

  public UUID getId() {
    return id;
  }

  public Map<String, String> getName() {
    return name;
  }

  public List<OfferingModel> getOfferings() {
    return offerings;
  }
}
