package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.infrastructure.persistence.entity.CategoryEntity;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.CategoryProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryQueryRepository extends JpaRepository<CategoryEntity, UUID> {
  @Query(
"""
    select
        c.id as id,
        c.catalog.id as catalogId,
        ct.business.id as businessId,
        t.id.language as language,
        t.name as name
    from CategoryEntity c
    join c.translations t
    join c.catalog ct
    where ct.id =:catalogId
    and ct.business.id =:businessId
""")
  List<CategoryProjection> findAllByBusinessIdAndCatalogId(
      @Param("businessId") UUID businessId, @Param("catalogId") UUID catalogId);
}
