package dev.thiagooliveira.vitrify.infrastructure.persistence;

import dev.thiagooliveira.vitrify.application.usecase.*;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import dev.thiagooliveira.vitrify.domain.model.SocialLink;
import dev.thiagooliveira.vitrify.domain.model.SocialLinkType;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import java.util.stream.Collectors;
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
  @Autowired private UpdateBusinessUseCase updateBusinessUseCase;
  @Autowired private UpdateBusinessSocialLinkUseCase updateBusinessSocialLinkUseCase;
  @Autowired private CreateCatalogUseCase createCatalogUseCase;
  @Autowired private CreateCategoryUseCase createCategoryUseCase;
  @Autowired private CreateOfferingUseCase createOfferingUseCase;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private AppSeedProperties appSeedProperties;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM business", Long.class);
    if (count != null && count > 0) {
      log.info("Business already created");
      return;
    }

    AppSeedProperties.Business businessConfig = appSeedProperties.getBusiness();

    var business =
        createBusinessUseCase.execute(
            businessConfig.getName(),
            businessConfig.getSupportedLanguages().stream()
                .map(Language::valueOf)
                .collect(Collectors.toSet()));
    appSeedProperties.setBusinessId(business.getId());
    business =
        updateBusinessUseCase.execute(
            business.getId(),
            business.getName(),
            businessConfig.getAddress(),
            business.getSupportedLanguages());

    for (AppSeedProperties.SocialLink socialLinkConfig : businessConfig.getSocialLinks()) {
      updateBusinessSocialLinkUseCase.execute(
          business.getId(),
          SocialLink.create(
              SocialLinkType.valueOf(socialLinkConfig.getType()), socialLinkConfig.getUrl()));
    }

    for (AppSeedProperties.Catalog catalogConfig : businessConfig.getCatalogs()) {
      var catalog =
          createCatalogUseCase.execute(
              business.getId(),
              LocalizedContent.of(Language.PT, catalogConfig.getName().get("PT"))
                  .with(Language.EN, catalogConfig.getName().get("EN")));

      for (AppSeedProperties.Category categoryConfig : catalogConfig.getCategories()) {
        var category =
            createCategoryUseCase.execute(
                business.getId(),
                catalog.getId(),
                LocalizedContent.of(Language.PT, categoryConfig.getName().get("PT"))
                    .with(Language.EN, categoryConfig.getName().get("EN")));

        for (AppSeedProperties.Offering offeringConfig : categoryConfig.getOfferings()) {
          createOfferingUseCase.execute(
              business.getId(),
              catalog.getId(),
              category.getId(),
              offeringConfig.getPrice(),
              LocalizedContent.of(Language.PT, offeringConfig.getName().get("PT"))
                  .with(Language.EN, offeringConfig.getName().get("EN")),
              LocalizedContent.of(Language.PT, offeringConfig.getDescription().get("PT"))
                  .with(Language.EN, offeringConfig.getDescription().get("EN")),
              offeringConfig.getImages());
        }
      }
    }

    log.info(
        String.format(
            "Demo business '%s' created, alias %s", business.getName(), business.getAlias()));
  }
}
