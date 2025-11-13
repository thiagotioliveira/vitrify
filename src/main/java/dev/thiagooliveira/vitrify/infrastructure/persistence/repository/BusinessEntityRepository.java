package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.infrastructure.persistence.entity.BusinessEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityRepository extends JpaRepository<BusinessEntity, UUID> {

  Optional<BusinessEntity> findByAlias(String alias);
}
