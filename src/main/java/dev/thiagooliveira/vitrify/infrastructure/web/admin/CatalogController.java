package dev.thiagooliveira.vitrify.infrastructure.web.admin;

import dev.thiagooliveira.vitrify.application.query.usecase.GetCatalogUseCase;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import dev.thiagooliveira.vitrify.infrastructure.web.admin.dto.CatalogModel;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class CatalogController {

  private final AppSeedProperties appSeedProperties;
  private final GetCatalogUseCase getCatalogUseCase;

  public CatalogController(
      AppSeedProperties appSeedProperties, GetCatalogUseCase getCatalogUseCase) {
    this.appSeedProperties = appSeedProperties;
    this.getCatalogUseCase = getCatalogUseCase;
  }

  @GetMapping("/protected/catalog")
  public ModelAndView index() {
    var catalogs =
        this.getCatalogUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
            .map(CatalogModel::new)
            .toList();
    return new ModelAndView("protected/catalog-list", Map.of("catalogList", catalogs));
  }
}
