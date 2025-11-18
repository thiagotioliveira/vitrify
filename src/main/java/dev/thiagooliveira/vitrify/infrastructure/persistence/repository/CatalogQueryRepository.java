package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.infrastructure.persistence.entity.CatalogEntity;
import dev.thiagooliveira.vitrify.infrastructure.persistence.projection.CatalogProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CatalogQueryRepository extends JpaRepository<CatalogEntity, UUID> {
  @Query(
"""
    select
        c.id as id,
        c.business.id as businessId,
        t.id.language as language,
        t.name as name
    from CatalogEntity c
    join c.translations t
    where c.business.id =:businessId
""")
  List<CatalogProjection> findAllByBusinessId(@Param("businessId") UUID businessId);

  @Query(
      """
                select
                    c.id as id,
                    c.business.id as businessId,
                    t.id.language as language,
                    t.name as name
                from CatalogEntity c
                join c.translations t
                where c.business.id =:businessId
                            and c.id =:id
            """)
  List<CatalogProjection> findByIdAndBusinessId(
      @Param("id") UUID id, @Param("businessId") UUID businessId);
}
