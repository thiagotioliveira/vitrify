package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import dev.thiagooliveira.vitrify.application.query.CategoryQuery;
import dev.thiagooliveira.vitrify.application.query.dto.CategorySummary;
import dev.thiagooliveira.vitrify.infrastructure.persistence.mapper.CategoryMapper;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CategoryQueryAdapter implements CategoryQuery {

  private final CategoryQueryRepository categoryQueryRepository;

  public CategoryQueryAdapter(CategoryQueryRepository categoryQueryRepository) {
    this.categoryQueryRepository = categoryQueryRepository;
  }

  @Override
  public List<CategorySummary> findAllByBusinessId(UUID businessId) {
    return CategoryMapper.map(this.categoryQueryRepository.findAllByBusinessId(businessId));
  }
}
