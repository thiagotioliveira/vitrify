package dev.thiagooliveira.vitrify.infrastructure.web.dto;

import dev.thiagooliveira.vitrify.domain.model.Catalog;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CatalogModel {

  private final UUID id;
  private final Map<String, String> name;
  private final List<CategoryModel> categories;

  public CatalogModel(Catalog catalog) {
    this.id = catalog.getId();
    this.name =
        catalog.getName().getContentMap().entrySet().stream()
            .collect(
                Collectors.toMap(
                    entry -> entry.getKey().toString(), entry -> entry.getValue().content()));
    this.categories =
        catalog.getCategories().stream().map(CategoryModel::new).collect(Collectors.toList());
  }

  public UUID getId() {
    return id;
  }

  public Map<String, String> getName() {
    return name;
  }

  public List<CategoryModel> getCategories() {
    return categories;
  }
}
