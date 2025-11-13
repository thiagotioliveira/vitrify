package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import dev.thiagooliveira.vitrify.infrastructure.persistence.entity.BusinessEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BusinessRepositoryAdapter implements BusinessRepository {

  private final BusinessEntityRepository businessEntityRepository;

  public BusinessRepositoryAdapter(BusinessEntityRepository businessEntityRepository) {
    this.businessEntityRepository = businessEntityRepository;
  }

  @Override
  public Optional<Business> findById(UUID id) {
    return this.businessEntityRepository.findById(id).map(BusinessEntity::toDomain);
  }

  @Override
  public void save(Business business) {
    this.businessEntityRepository.save(BusinessEntity.fromDomain(business));
  }
}
