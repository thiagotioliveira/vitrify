package dev.thiagooliveira.vitrify.infrastructure.persistence.mapper;

import dev.thiagooliveira.vitrify.application.query.dto.BusinessSummary;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.BusinessProjection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class BusinessMapper {

  public static Optional<BusinessSummary> map(List<BusinessProjection> list) {
    if (list == null || list.isEmpty()) {
      return Optional.empty();
    } else {
      var projection = list.get(0);
      var supportedLanguages = new HashSet<Language>();
      supportedLanguages.add(projection.getLanguage());
      var businessSummary =
          BusinessSummary.load(
              projection.getId(),
              projection.getName(),
              projection.getAddress(),
              supportedLanguages);
      for (int i = 1; i < list.size(); i++) {
        var p = list.get(i);
        businessSummary.getSupportedLanguages().add(p.getLanguage());
      }
      return Optional.of(businessSummary);
    }
  }
}
