package dev.thiagooliveira.vitrify.application.query;

import dev.thiagooliveira.vitrify.application.query.dto.OfferingSummary;
import java.util.List;
import java.util.UUID;

public interface OfferingQuery {

  List<OfferingSummary> findAllByBusinessId(UUID businessId);
}
