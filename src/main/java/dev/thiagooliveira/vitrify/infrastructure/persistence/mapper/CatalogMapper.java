package dev.thiagooliveira.vitrify.infrastructure.persistence.mapper;

import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.CatalogProjection;
import java.util.ArrayList;
import java.util.List;

public class CatalogMapper {

  public static List<CatalogSummary> map(List<CatalogProjection> list) {
    var result = new ArrayList<CatalogSummary>();
    list.forEach(
        c -> {
          var op = result.stream().filter(v -> v.getId().equals(c.getId())).findFirst();
          if (op.isPresent()) {
            op.get().getName().with(c.getLanguage(), c.getName());
          } else {
            result.add(
                CatalogSummary.load(c.getBusinessId(), c.getId(), c.getLanguage(), c.getName()));
          }
        });
    return result;
  }
}
