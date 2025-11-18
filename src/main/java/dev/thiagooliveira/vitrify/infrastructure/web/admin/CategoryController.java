package dev.thiagooliveira.vitrify.infrastructure.web.admin;

import dev.thiagooliveira.vitrify.application.query.usecase.GetCatalogUseCase;
import dev.thiagooliveira.vitrify.application.query.usecase.GetCategoryUseCase;
import dev.thiagooliveira.vitrify.application.usecase.CreateCategoryUseCase;
import dev.thiagooliveira.vitrify.application.usecase.DeleteCategoryUseCase;
import dev.thiagooliveira.vitrify.application.usecase.UpdateCategoryUseCase;
import dev.thiagooliveira.vitrify.domain.exception.CategoryNotFoundException;
import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import dev.thiagooliveira.vitrify.infrastructure.web.admin.dto.CatalogModel;
import dev.thiagooliveira.vitrify.infrastructure.web.admin.dto.CategoryModel;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/protected/category")
public class CategoryController {

  private final AppSeedProperties appSeedProperties;
  private final GetCatalogUseCase getCatalogUseCase;
  private final GetCategoryUseCase getCategoryUseCase;
  private final CreateCategoryUseCase createCategoryUseCase;
  private final UpdateCategoryUseCase updateCategoryUseCase;
  private final DeleteCategoryUseCase deleteCategoryUseCase;

  public CategoryController(
      AppSeedProperties appSeedProperties,
      GetCatalogUseCase getCatalogUseCase,
      GetCategoryUseCase getCategoryUseCase,
      CreateCategoryUseCase createCategoryUseCase,
      UpdateCategoryUseCase updateCategoryUseCase,
      DeleteCategoryUseCase deleteCategoryUseCase) {
    this.appSeedProperties = appSeedProperties;
    this.getCatalogUseCase = getCatalogUseCase;
    this.getCategoryUseCase = getCategoryUseCase;
    this.createCategoryUseCase = createCategoryUseCase;
    this.updateCategoryUseCase = updateCategoryUseCase;
    this.deleteCategoryUseCase = deleteCategoryUseCase;
  }

  @GetMapping
  public ModelAndView index() {
    var categories =
        this.getCategoryUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
            .map(CategoryModel::new)
            .toList();
    return new ModelAndView("protected/category-list", Map.of("categories", categories));
  }

  @GetMapping("/new")
  public ModelAndView newForm() {
    var catalogs =
        this.getCatalogUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
            .map(CatalogModel::new)
            .toList();
    var supportedLanguages =
        appSeedProperties.getBusiness().getSupportedLanguages().stream()
            .map(Language::valueOf)
            .toList();
    return new ModelAndView(
        "protected/category-form",
        Map.of(
            "category",
            new CategoryModel(appSeedProperties.getBusinessId()),
            "catalogs",
            catalogs.stream()
                .map(
                    c ->
                        Map.of(
                            "id", c.getId(),
                            "label", c.multiLangName(supportedLanguages)))
                .toList(),
            "supportedLanguages",
            supportedLanguages));
  }

  @GetMapping("/edit/{id}")
  public ModelAndView edit(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
    try {
      var category =
          this.getCategoryUseCase
              .execute(this.appSeedProperties.getBusinessId(), id)
              .map(CategoryModel::new)
              .orElseThrow(CategoryNotFoundException::new);
      var catalogs =
          this.getCatalogUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
              .map(CatalogModel::new)
              .toList();
      var supportedLanguages =
          appSeedProperties.getBusiness().getSupportedLanguages().stream()
              .map(Language::valueOf)
              .toList();
      return new ModelAndView(
          "protected/category-form",
          Map.of(
              "category",
              category,
              "catalogs",
              catalogs.stream()
                  .map(
                      c ->
                          Map.of(
                              "id", c.getId(),
                              "label", c.multiLangName(supportedLanguages)))
                  .toList(),
              "supportedLanguages",
              supportedLanguages));
    } catch (DomainException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return new ModelAndView("redirect:/protected/category");
    }
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
    try {
      this.deleteCategoryUseCase.execute(this.appSeedProperties.getBusinessId(), id);
      redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
    } catch (DomainException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/protected/category";
  }

  @PostMapping("/save")
  public ModelAndView save(
      @ModelAttribute CategoryModel categoryModel, RedirectAttributes redirectAttributes) {
    try {
      if (categoryModel.getId() == null) {
        var category =
            this.createCategoryUseCase.execute(
                this.appSeedProperties.getBusinessId(),
                categoryModel.getCatalogId(),
                categoryModel.getNameLocalizedContent());
      } else {
        var category =
            this.updateCategoryUseCase.execute(
                this.appSeedProperties.getBusinessId(),
                categoryModel.getCatalogId(),
                categoryModel.getId(),
                categoryModel.getNameLocalizedContent());
      }
      redirectAttributes.addFlashAttribute("successMessage", "Category saved successfully!");
      return new ModelAndView("redirect:/protected/category");
    } catch (DomainException e) {
      var catalogs =
          this.getCatalogUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
              .map(CatalogModel::new)
              .toList();
      var supportedLanguages =
          appSeedProperties.getBusiness().getSupportedLanguages().stream()
              .map(Language::valueOf)
              .toList();
      return new ModelAndView(
          "protected/category-form",
          Map.of(
              "category",
              categoryModel,
              "catalogs",
              catalogs.stream()
                  .map(
                      c ->
                          Map.of(
                              "id", c.getId(),
                              "label", c.multiLangName(supportedLanguages)))
                  .toList(),
              "supportedLanguages",
              supportedLanguages,
              "errorMessage",
              e.getMessage()));
    }
  }
}
