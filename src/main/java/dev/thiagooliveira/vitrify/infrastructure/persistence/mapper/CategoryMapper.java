package dev.thiagooliveira.vitrify.infrastructure.persistence.mapper;

import dev.thiagooliveira.vitrify.application.query.dto.CategorySummary;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.CategoryProjection;
import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

  public static List<CategorySummary> map(List<CategoryProjection> list) {
    var result = new ArrayList<CategorySummary>();
    list.forEach(
        c -> {
          var op = result.stream().filter(v -> v.getId().equals(c.getId())).findFirst();
          if (op.isPresent()) {
            op.get().getName().with(c.getLanguage(), c.getName());
            op.get().getCatalogName().with(c.getCatalogLanguage(), c.getCatalogName());
          } else {
            result.add(
                CategorySummary.load(
                    c.getBusinessId(),
                    c.getCatalogId(),
                    c.getCatalogLanguage(),
                    c.getCatalogName(),
                    c.getId(),
                    c.getLanguage(),
                    c.getName()));
          }
        });
    return result;
  }
}
