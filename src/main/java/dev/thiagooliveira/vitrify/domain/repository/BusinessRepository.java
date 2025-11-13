package dev.thiagooliveira.vitrify.domain.repository;

import dev.thiagooliveira.vitrify.domain.model.Business;
import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository {
  Optional<Business> findById(UUID id);

  void save(Business business);
}
