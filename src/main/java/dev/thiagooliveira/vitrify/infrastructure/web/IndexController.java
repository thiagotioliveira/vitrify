package dev.thiagooliveira.vitrify.infrastructure.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.vitrify.application.usecase.GetBusinessUseCase;
import dev.thiagooliveira.vitrify.infrastructure.web.dto.BusinessModel;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class IndexController {

  private final GetBusinessUseCase getBusinessUseCase;

  public IndexController(GetBusinessUseCase getBusinessUseCase) {
    this.getBusinessUseCase = getBusinessUseCase;
  }

  @GetMapping("/p/{alias}")
  public ModelAndView index(@PathVariable("alias") String alias) throws JsonProcessingException {
    var business =
        this.getBusinessUseCase.findByAlias(alias).orElseThrow(IllegalArgumentException::new);
    BusinessModel businessModel = new BusinessModel(business);
    return new ModelAndView(
        "index",
        Map.of(
            "business",
            businessModel,
            "businessJson",
            new ObjectMapper().writeValueAsString(businessModel)));
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public void root() {}
}
