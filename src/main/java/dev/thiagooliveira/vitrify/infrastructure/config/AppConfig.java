package dev.thiagooliveira.vitrify.infrastructure.config;

import dev.thiagooliveira.vitrify.application.query.BusinessQuery;
import dev.thiagooliveira.vitrify.application.query.CatalogQuery;
import dev.thiagooliveira.vitrify.application.query.CategoryQuery;
import dev.thiagooliveira.vitrify.application.query.OfferingQuery;
import dev.thiagooliveira.vitrify.application.query.usecase.GetCatalogUseCase;
import dev.thiagooliveira.vitrify.application.query.usecase.GetCategoryUseCase;
import dev.thiagooliveira.vitrify.application.query.usecase.GetOfferingUseCase;
import dev.thiagooliveira.vitrify.application.usecase.*;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  CreateBusinessUseCase createBusinessUseCase(BusinessRepository businessRepository) {
    return new CreateBusinessUseCase(businessRepository);
  }

  @Bean
  UpdateBusinessUseCase updateBusinessUseCase(BusinessRepository businessRepository) {
    return new UpdateBusinessUseCase(businessRepository);
  }

  @Bean
  UpdateBusinessSocialLinkUseCase updateBusinessSocialLinkUseCase(
      BusinessRepository businessRepository) {
    return new UpdateBusinessSocialLinkUseCase(businessRepository);
  }

  @Bean
  GetBusinessUseCase getBusinessUseCase(BusinessRepository businessRepository) {
    return new GetBusinessUseCase(businessRepository);
  }

  @Bean
  dev.thiagooliveira.vitrify.application.query.usecase.GetBusinessUseCase getBusinessQueryUseCase(
      BusinessQuery businessQuery) {
    return new dev.thiagooliveira.vitrify.application.query.usecase.GetBusinessUseCase(
        businessQuery);
  }

  @Bean
  GetCatalogUseCase getCatalogUseCase(CatalogQuery catalogQuery) {
    return new GetCatalogUseCase(catalogQuery);
  }

  @Bean
  CreateCatalogUseCase createCatalogUseCase(BusinessRepository businessRepository) {
    return new CreateCatalogUseCase(businessRepository);
  }

  @Bean
  UpdateCatalogUseCase updateCatalogUseCase(BusinessRepository businessRepository) {
    return new UpdateCatalogUseCase(businessRepository);
  }

  @Bean
  DeleteCatalogUseCase deleteCatalogUseCase(BusinessRepository businessRepository) {
    return new DeleteCatalogUseCase(businessRepository);
  }

  @Bean
  GetCategoryUseCase getCategoryUseCase(CategoryQuery categoryQuery) {
    return new GetCategoryUseCase(categoryQuery);
  }

  @Bean
  CreateCategoryUseCase createCategoryUseCase(BusinessRepository businessRepository) {
    return new CreateCategoryUseCase(businessRepository);
  }

  @Bean
  UpdateCategoryUseCase updateCategoryUseCase(BusinessRepository businessRepository) {
    return new UpdateCategoryUseCase(businessRepository);
  }

  @Bean
  DeleteCategoryUseCase deleteCategoryUseCase(BusinessRepository businessRepository) {
    return new DeleteCategoryUseCase(businessRepository);
  }

  @Bean
  GetOfferingUseCase getOfferingUseCase(OfferingQuery offeringQuery) {
    return new GetOfferingUseCase(offeringQuery);
  }

  @Bean
  CreateOfferingUseCase createOfferingUseCase(BusinessRepository businessRepository) {
    return new CreateOfferingUseCase(businessRepository);
  }
}
