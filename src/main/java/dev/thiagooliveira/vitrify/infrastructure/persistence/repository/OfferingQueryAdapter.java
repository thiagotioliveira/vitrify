package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.application.query.OfferingQuery;
import dev.thiagooliveira.vitrify.application.query.dto.OfferingSummary;
import dev.thiagooliveira.vitrify.infrastructure.persistence.mapper.OfferingMapper;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OfferingQueryAdapter implements OfferingQuery {

  private final OfferingQueryRepository offeringQueryRepository;

  public OfferingQueryAdapter(OfferingQueryRepository offeringQueryRepository) {
    this.offeringQueryRepository = offeringQueryRepository;
  }

  @Override
  public List<OfferingSummary> findAllByBusinessId(UUID businessId) {
    return OfferingMapper.map(this.offeringQueryRepository.findAllByBusinessId(businessId));
  }
}
