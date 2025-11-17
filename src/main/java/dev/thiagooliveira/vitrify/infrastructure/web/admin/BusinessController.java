package dev.thiagooliveira.vitrify.infrastructure.web.admin;

import dev.thiagooliveira.vitrify.application.query.dto.BusinessSummary;
import dev.thiagooliveira.vitrify.application.query.usecase.GetBusinessUseCase;
import dev.thiagooliveira.vitrify.application.usecase.UpdateBusinessUseCase;
import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import dev.thiagooliveira.vitrify.infrastructure.web.admin.dto.BusinessModel;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class BusinessController {

  private final AppSeedProperties appSeedProperties;
  private final GetBusinessUseCase getBusinessQueryUseCase;
  private final UpdateBusinessUseCase updateBusinessUseCase;

  public BusinessController(
      AppSeedProperties appSeedProperties,
      GetBusinessUseCase getBusinessQueryUseCase,
      UpdateBusinessUseCase updateBusinessUseCase) {
    this.appSeedProperties = appSeedProperties;
    this.getBusinessQueryUseCase = getBusinessQueryUseCase;
    this.updateBusinessUseCase = updateBusinessUseCase;
  }

  @GetMapping("/protected/business")
  public ModelAndView index() {
    var business =
        this.getBusinessQueryUseCase
            .execute(this.appSeedProperties.getBusinessId())
            .orElseThrow(BusinessNotFoundException::new);
    return new ModelAndView(
        "protected/business-form", Map.of("business", new BusinessModel(business)));
  }

  @PostMapping("/protected/business/save")
  public ModelAndView save(@ModelAttribute BusinessSummary businessSummary) {
    try {
      var business =
          this.updateBusinessUseCase.execute(
              businessSummary.getId(),
              businessSummary.getName(),
              businessSummary.getAddress(),
              businessSummary.getSupportedLanguages());
      return new ModelAndView(
          "protected/business-form",
          Map.of(
              "business",
              new BusinessModel(business),
              "successMessage",
              "Business updated successfully!"));
    } catch (DomainException e) {
      return new ModelAndView(
          "protected/business-form",
          Map.of("business", new BusinessModel(businessSummary), "errorMessage", e.getMessage()));
    }
  }
}
