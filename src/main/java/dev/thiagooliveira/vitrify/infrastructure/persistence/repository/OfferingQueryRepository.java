package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.infrastructure.persistence.entity.OfferingEntity;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.OfferingProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OfferingQueryRepository extends JpaRepository<OfferingEntity, UUID> {
  @Query(
"""
    select
        o.id as id,
        o.category.id as categoryId,
        ca.id as catalogId,
        ca.business.id as businessId,
        t.id.language as language,
        t.name as name,
        t.description as description
    from OfferingEntity o
    join o.translations t
    join o.category ct
    join ct.catalog ca
    where ca.id =:catalogId
    and ca.business.id =:businessId
    and ct.id =:categoryId
""")
  List<OfferingProjection> findAllByBusinessIdAndCatalogIdAndCategoryId(
      @Param("businessId") UUID businessId,
      @Param("catalogId") UUID catalogId,
      @Param("categoryId") UUID categoryId);
}
