package dev.thiagooliveira.vitrify.infrastructure.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.vitrify.application.query.dto.CatalogSummary;
import dev.thiagooliveira.vitrify.application.query.dto.CategorySummary;
import dev.thiagooliveira.vitrify.application.query.usecase.GetCatalogUseCase;
import dev.thiagooliveira.vitrify.application.query.usecase.GetCategoryUseCase;
import dev.thiagooliveira.vitrify.infrastructure.config.AppConfig;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import dev.thiagooliveira.vitrify.infrastructure.persistence.InitDataCommandLine;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@ImportAutoConfiguration(
    classes = {
      DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      LiquibaseAutoConfiguration.class
    })
@Import({
  AppSeedProperties.class,
  AppConfig.class,
  BusinessRepositoryAdapter.class,
  CatalogQueryAdapter.class,
  CategoryQueryAdapter.class,
  OfferingQueryAdapter.class,
  InitDataCommandLine.class
})
class CategoryQueryRepositoryTest {

  @Autowired private GetCatalogUseCase getCatalogUseCase;

  @Autowired private GetCategoryUseCase getCategoryUseCase;

  @Autowired private AppSeedProperties appSeedProperties;

  @BeforeEach
  void setUp() {}

  @Test
  void findAllByBusinessIdAndCatalogId() {
    List<CatalogSummary> catalogs = getCatalogUseCase.execute(appSeedProperties.getBusinessId());
    List<CategorySummary> result =
        getCategoryUseCase.execute(appSeedProperties.getBusinessId(), catalogs.get(0).getId());
    assertFalse(result.isEmpty());
  }
}
