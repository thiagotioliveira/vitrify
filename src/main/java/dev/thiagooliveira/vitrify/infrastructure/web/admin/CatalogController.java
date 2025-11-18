package dev.thiagooliveira.vitrify.infrastructure.web.admin;

import dev.thiagooliveira.vitrify.application.query.usecase.GetCatalogUseCase;
import dev.thiagooliveira.vitrify.application.usecase.CreateCatalogUseCase;
import dev.thiagooliveira.vitrify.application.usecase.DeleteCatalogUseCase;
import dev.thiagooliveira.vitrify.application.usecase.UpdateCatalogUseCase;
import dev.thiagooliveira.vitrify.domain.exception.CatalogNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import dev.thiagooliveira.vitrify.infrastructure.web.admin.dto.CatalogModel;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/protected/catalog")
public class CatalogController {

  private final AppSeedProperties appSeedProperties;
  private final GetCatalogUseCase getCatalogUseCase;
  private final CreateCatalogUseCase createCatalogUseCase;
  private final UpdateCatalogUseCase updateCatalogUseCase;
  private final DeleteCatalogUseCase deleteCatalogUseCase;

  public CatalogController(
      AppSeedProperties appSeedProperties,
      GetCatalogUseCase getCatalogUseCase,
      CreateCatalogUseCase createCatalogUseCase,
      UpdateCatalogUseCase updateCatalogUseCase,
      DeleteCatalogUseCase deleteCatalogUseCase) {
    this.appSeedProperties = appSeedProperties;
    this.getCatalogUseCase = getCatalogUseCase;
    this.createCatalogUseCase = createCatalogUseCase;
    this.updateCatalogUseCase = updateCatalogUseCase;
    this.deleteCatalogUseCase = deleteCatalogUseCase;
  }

  @GetMapping
  public ModelAndView index() {
    var catalogs =
        this.getCatalogUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
            .map(CatalogModel::new)
            .toList();
    return new ModelAndView("protected/catalog-list", Map.of("catalogs", catalogs));
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable("id") UUID id) {
    var catalog =
        this.getCatalogUseCase
            .execute(this.appSeedProperties.getBusinessId(), id)
            .map(CatalogModel::new)
            .orElseThrow(CatalogNotFoundException::new);
    return new ModelAndView(
        "protected/catalog-form",
        Map.of(
            "catalog",
            catalog,
            "supportedLanguages",
            appSeedProperties.getBusiness().getSupportedLanguages().stream()
                .map(Language::valueOf)
                .toList()));
  }

  @GetMapping("/new")
  public ModelAndView newForm() {
    return new ModelAndView(
        "protected/catalog-form",
        Map.of(
            "catalog",
            new CatalogModel(appSeedProperties.getBusinessId()),
            "supportedLanguages",
            appSeedProperties.getBusiness().getSupportedLanguages().stream()
                .map(Language::valueOf)
                .toList()));
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
    try {
      this.deleteCatalogUseCase.execute(this.appSeedProperties.getBusinessId(), id);
      redirectAttributes.addFlashAttribute("successMessage", "Catalog deleted successfully!");
    } catch (DomainException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/protected/catalog";
  }

  @PostMapping("/save")
  public ModelAndView save(
      @ModelAttribute CatalogModel catalogModel, RedirectAttributes redirectAttributes) {
    try {
      if (catalogModel.getId() == null) {
        var catalog =
            this.createCatalogUseCase.execute(
                this.appSeedProperties.getBusinessId(), catalogModel.getNameLocalizedContent());
      } else {
        var catalog =
            this.updateCatalogUseCase.execute(
                this.appSeedProperties.getBusinessId(),
                catalogModel.getId(),
                catalogModel.getNameLocalizedContent());
      }
      redirectAttributes.addFlashAttribute("successMessage", "Catalog saved successfully!");
      return new ModelAndView("redirect:/protected/catalog");
    } catch (DomainException e) {
      return new ModelAndView(
          "protected/catalog-form",
          Map.of(
              "catalog",
              catalogModel,
              "supportedLanguages",
              appSeedProperties.getBusiness().getSupportedLanguages().stream()
                  .map(Language::valueOf)
                  .toList(),
              "errorMessage",
              e.getMessage()));
    }
  }
}
