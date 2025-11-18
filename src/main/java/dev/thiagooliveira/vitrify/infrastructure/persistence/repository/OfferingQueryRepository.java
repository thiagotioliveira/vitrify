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
        ttt.id.language as categoryLanguage,
        ttt.name as categoryName,
        ca.id as catalogId,
        tt.id.language as catalogLanguage,
        tt.name as catalogName,
        ca.business.id as businessId,
        t.id.language as language,
        t.name as name,
        t.description as description
    from OfferingEntity o
    join o.translations t
    join o.category ct
    join ct.translations ttt
    join ct.catalog ca
    join ca.translations tt
    where ca.business.id =:businessId
""")
  List<OfferingProjection> findAllByBusinessId(@Param("businessId") UUID businessId);
}
