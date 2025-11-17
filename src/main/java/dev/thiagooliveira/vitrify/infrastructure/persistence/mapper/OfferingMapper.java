package dev.thiagooliveira.vitrify.infrastructure.persistence.mapper;

import dev.thiagooliveira.vitrify.application.query.dto.OfferingSummary;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.OfferingProjection;
import java.util.ArrayList;
import java.util.List;

public class OfferingMapper {

  public static List<OfferingSummary> map(List<OfferingProjection> list) {
    var result = new ArrayList<OfferingSummary>();
    list.forEach(
        c -> {
          var op = result.stream().filter(v -> v.getId().equals(c.getId())).findFirst();
          if (op.isPresent()) {
            op.get().getName().with(c.getLanguage(), c.getName());
            op.get().getDescription().with(c.getLanguage(), c.getDescription());
          } else {
            result.add(
                OfferingSummary.load(
                    c.getBusinessId(),
                    c.getCatalogId(),
                    c.getCategoryId(),
                    c.getId(),
                    c.getLanguage(),
                    c.getName(),
                    c.getDescription()));
          }
        });
    return result;
  }
}
