package dev.thiagooliveira.vitrify.infrastructure.web.admin;

import dev.thiagooliveira.vitrify.application.query.usecase.GetOfferingUseCase;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import dev.thiagooliveira.vitrify.infrastructure.web.admin.dto.OfferingModel;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/protected/offering")
public class OfferingController {

  private final AppSeedProperties appSeedProperties;
  private final GetOfferingUseCase getOfferingUseCase;

  public OfferingController(
      AppSeedProperties appSeedProperties, GetOfferingUseCase getOfferingUseCase) {
    this.appSeedProperties = appSeedProperties;
    this.getOfferingUseCase = getOfferingUseCase;
  }

  @GetMapping
  public ModelAndView index() {
    var offerings =
        this.getOfferingUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
            .map(OfferingModel::new)
            .toList();
    return new ModelAndView("protected/offering-list", Map.of("offerings", offerings));
  }
}
