package dev.thiagooliveira.vitrify.infrastructure.persistence;

import dev.thiagooliveira.vitrify.application.usecase.*;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import java.math.BigDecimal;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitDataCommandLine implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(InitDataCommandLine.class);

  @Autowired private CreateBusinessUseCase createBusinessUseCase;

  @Autowired private CreateCatalogUseCase createCatalogUseCase;

  @Autowired private CreateCategoryUseCase createCategoryUseCase;

  @Autowired private CreateOfferingUseCase createOfferingUseCase;

  @Autowired private GetBusinessUseCase getBusinessUseCase;

  @Autowired private JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM business", Long.class);

    if (count != null && count > 0) {
      log.info("Business already created");
    } else {
      var business =
          createBusinessUseCase.execute("Padaria Portuguesa", Set.of(Language.PT, Language.EN));
      var catalogFood =
          createCatalogUseCase.execute(
              business.getId(),
              LocalizedContent.of(Language.PT, "Entradas").with(Language.EN, "Starters"));
      var categoryStarters =
          createCategoryUseCase.execute(
              business.getId(),
              catalogFood.getId(),
              LocalizedContent.of(Language.PT, "Sopas").with(Language.EN, "Soups"));
      var offering =
          createOfferingUseCase.execute(
              business.getId(),
              catalogFood.getId(),
              categoryStarters.getId(),
              BigDecimal.TEN,
              LocalizedContent.of(Language.PT, "Sopa do dia").with(Language.EN, "Soup of the day"),
              LocalizedContent.of(Language.PT, "Sopa caseira").with(Language.EN, "Homemade soup"));
      log.info("Business created");
    }
  }
}
