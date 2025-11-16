package dev.thiagooliveira.vitrify.infrastructure.config;

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
  CreateCatalogUseCase createCatalogUseCase(BusinessRepository businessRepository) {
    return new CreateCatalogUseCase(businessRepository);
  }

  @Bean
  CreateCategoryUseCase createCategoryUseCase(BusinessRepository businessRepository) {
    return new CreateCategoryUseCase(businessRepository);
  }

  @Bean
  CreateOfferingUseCase createOfferingUseCase(BusinessRepository businessRepository) {
    return new CreateOfferingUseCase(businessRepository);
  }
}
