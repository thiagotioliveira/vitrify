package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.application.query.BusinessQuery;
import dev.thiagooliveira.vitrify.application.query.dto.BusinessSummary;
import dev.thiagooliveira.vitrify.infrastructure.persistence.mapper.BusinessMapper;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BusinessQueryAdapter implements BusinessQuery {

  private final BusinessQueryRepository businessQueryRepository;

  public BusinessQueryAdapter(BusinessQueryRepository businessQueryRepository) {
    this.businessQueryRepository = businessQueryRepository;
  }

  @Override
  public Optional<BusinessSummary> findById(UUID id) {
    return BusinessMapper.map(this.businessQueryRepository.findAById(id));
  }
}
