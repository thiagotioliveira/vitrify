package dev.thiagooliveira.vitrify.infrastructure.web.admin;

import dev.thiagooliveira.vitrify.application.query.usecase.GetCategoryUseCase;
import dev.thiagooliveira.vitrify.infrastructure.config.AppSeedProperties;
import dev.thiagooliveira.vitrify.infrastructure.web.admin.dto.CategoryModel;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/protected/category")
public class CategoryController {

  private final AppSeedProperties appSeedProperties;
  private final GetCategoryUseCase getCategoryUseCase;

  public CategoryController(
      AppSeedProperties appSeedProperties, GetCategoryUseCase getCategoryUseCase) {
    this.appSeedProperties = appSeedProperties;
    this.getCategoryUseCase = getCategoryUseCase;
  }

  @GetMapping
  public ModelAndView index() {
    var categories =
        this.getCategoryUseCase.execute(this.appSeedProperties.getBusinessId()).stream()
            .map(CategoryModel::new)
            .toList();
    return new ModelAndView("protected/category-list", Map.of("categories", categories));
  }
}
