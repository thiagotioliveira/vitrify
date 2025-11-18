package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.application.query.CatalogQuery;
import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import dev.thiagooliveira.vitrify.infrastructure.persistence.mapper.CatalogMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CatalogQueryAdapter implements CatalogQuery {

  private final CatalogQueryRepository catalogQueryRepository;

  public CatalogQueryAdapter(CatalogQueryRepository catalogQueryRepository) {
    this.catalogQueryRepository = catalogQueryRepository;
  }

  @Override
  public List<CatalogSummary> findAllByBusinessId(UUID businessId) {
    return CatalogMapper.map(this.catalogQueryRepository.findAllByBusinessId(businessId));
  }

  @Override
  public Optional<CatalogSummary> findByIdAndBusinessId(UUID id, UUID businessId) {
    return CatalogMapper.map(this.catalogQueryRepository.findByIdAndBusinessId(id, businessId))
        .stream()
        .findFirst();
  }
}
