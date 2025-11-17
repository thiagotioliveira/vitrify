package dev.thiagooliveira.vitrify.infrastructure.persistence.projection;

import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import dev.thiagooliveira.vitrify.domain.model.Language;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface CatalogProjection {
  UUID getBusinessId();

  UUID getId();

  Language getLanguage();

  String getName();

  default List<CatalogSummary> map(List<CatalogProjection> list) {
    var result = new ArrayList<CatalogSummary>();
    list.forEach(
        c -> {
          var catalogOp = result.stream().filter(v -> v.getId().equals(c.getId())).findFirst();
          if (catalogOp.isPresent()) {
            catalogOp.get().getName().with(c.getLanguage(), c.getName());
          } else {
            result.add(
                CatalogSummary.load(c.getBusinessId(), c.getId(), c.getLanguage(), c.getName()));
          }
        });
    return result;
  }
}
