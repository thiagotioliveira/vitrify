package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.infrastructure.persistence.entity.BusinessEntity;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.BusinessProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BusinessQueryRepository extends JpaRepository<BusinessEntity, UUID> {
  @Query(
"""
    select
        b.id as id,
        b.name as name,
        b.address as address,
        l as language
    from BusinessEntity b
    join b.supportedLanguages l
    where b.id =:businessId
""")
  List<BusinessProjection> findAById(@Param("businessId") UUID id);
}
