package dev.thiagooliveira.vitrify.infrastructure.persistence;

import dev.thiagooliveira.vitrify.application.usecase.*;
import dev.thiagooliveira.vitrify.domain.model.Language;
import dev.thiagooliveira.vitrify.domain.model.LocalizedContent;
import java.math.BigDecimal;
import java.util.Set;
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
  @Autowired private CreateCatalogUseCase createCatalogUseCase;
  @Autowired private CreateCategoryUseCase createCategoryUseCase;
  @Autowired private CreateOfferingUseCase createOfferingUseCase;
  @Autowired private JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM business", Long.class);

    if (count != null && count > 0) {
      log.info("Business already created");
      return;
    }

    // --- CREATE BUSINESS ---
    var business = createBusinessUseCase.execute("Sabor & Arte", Set.of(Language.PT, Language.EN));

    // --- CATALOG: Almoço ---
    var catalogLunch =
        createCatalogUseCase.execute(
            business.getId(),
            LocalizedContent.of(Language.PT, "Almoço").with(Language.EN, "Lunch"));

    // Categories Lunch
    var categoryStarters =
        createCategoryUseCase.execute(
            business.getId(),
            catalogLunch.getId(),
            LocalizedContent.of(Language.PT, "Entradas").with(Language.EN, "Starters"));

    var categoryMain =
        createCategoryUseCase.execute(
            business.getId(),
            catalogLunch.getId(),
            LocalizedContent.of(Language.PT, "Pratos Principais")
                .with(Language.EN, "Main Courses"));

    var categoryDessert =
        createCategoryUseCase.execute(
            business.getId(),
            catalogLunch.getId(),
            LocalizedContent.of(Language.PT, "Sobremesas").with(Language.EN, "Desserts"));

    // Offerings Lunch - Starters
    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryStarters.getId(),
        BigDecimal.valueOf(5.50),
        LocalizedContent.of(Language.PT, "Salada Caprese").with(Language.EN, "Caprese Salad"),
        LocalizedContent.of(Language.PT, "Tomate, mussarela e manjericão")
            .with(Language.EN, "Tomato, mozzarella, basil"));

    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryStarters.getId(),
        BigDecimal.valueOf(4.00),
        LocalizedContent.of(Language.PT, "Bruschetta").with(Language.EN, "Bruschetta"),
        LocalizedContent.of(Language.PT, "Pão, tomate, alho e azeite")
            .with(Language.EN, "Bread, tomato, garlic, olive oil"));

    // NEW Offering for Starters
    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryStarters.getId(),
        BigDecimal.valueOf(6.00),
        LocalizedContent.of(Language.PT, "Sopa Fria").with(Language.EN, "Cold Soup"),
        LocalizedContent.of(Language.PT, "Sopa refrescante do dia")
            .with(Language.EN, "Refreshing soup of the day"));

    // Offerings Lunch - Main Courses
    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryMain.getId(),
        BigDecimal.valueOf(12.00),
        LocalizedContent.of(Language.PT, "Spaghetti Carbonara")
            .with(Language.EN, "Spaghetti Carbonara"),
        LocalizedContent.of(Language.PT, "Massa com bacon e queijo")
            .with(Language.EN, "Pasta with bacon and cheese"));

    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryMain.getId(),
        BigDecimal.valueOf(15.00),
        LocalizedContent.of(Language.PT, "Frango Grelhado").with(Language.EN, "Grilled Chicken"),
        LocalizedContent.of(Language.PT, "Frango com legumes e arroz")
            .with(Language.EN, "Chicken with vegetables and rice"));

    // NEW Offering for Main Courses
    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryMain.getId(),
        BigDecimal.valueOf(14.50),
        LocalizedContent.of(Language.PT, "Peixe ao Forno").with(Language.EN, "Baked Fish"),
        LocalizedContent.of(Language.PT, "Peixe fresco assado com ervas")
            .with(Language.EN, "Fresh fish baked with herbs"));

    // Offerings Lunch - Dessert
    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryDessert.getId(),
        BigDecimal.valueOf(6.00),
        LocalizedContent.of(Language.PT, "Pudim de Leite").with(Language.EN, "Caramel Pudding"),
        LocalizedContent.of(Language.PT, "Pudim caseiro").with(Language.EN, "Homemade pudding"));

    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryDessert.getId(),
        BigDecimal.valueOf(5.50),
        LocalizedContent.of(Language.PT, "Mousse de Chocolate")
            .with(Language.EN, "Chocolate Mousse"),
        LocalizedContent.of(Language.PT, "Mousse cremosa de chocolate")
            .with(Language.EN, "Creamy chocolate mousse"));

    // NEW Offering for Dessert
    createOfferingUseCase.execute(
        business.getId(),
        catalogLunch.getId(),
        categoryDessert.getId(),
        BigDecimal.valueOf(6.50),
        LocalizedContent.of(Language.PT, "Tarte de Limão").with(Language.EN, "Lemon Tart"),
        LocalizedContent.of(Language.PT, "Tarte de limão com merengue")
            .with(Language.EN, "Lemon tart with meringue"));

    // --- CATALOG: Jantar ---
    var catalogDinner =
        createCatalogUseCase.execute(
            business.getId(),
            LocalizedContent.of(Language.PT, "Jantar").with(Language.EN, "Dinner"));

    // Categories Dinner
    var categoryDinnerStarters =
        createCategoryUseCase.execute(
            business.getId(),
            catalogDinner.getId(),
            LocalizedContent.of(Language.PT, "Entradas").with(Language.EN, "Starters"));

    var categoryDinnerMain =
        createCategoryUseCase.execute(
            business.getId(),
            catalogDinner.getId(),
            LocalizedContent.of(Language.PT, "Pratos Principais")
                .with(Language.EN, "Main Courses"));

    // Offerings Dinner - Starters
    createOfferingUseCase.execute(
        business.getId(),
        catalogDinner.getId(),
        categoryDinnerStarters.getId(),
        BigDecimal.valueOf(3.50),
        LocalizedContent.of(Language.PT, "Sopa do Dia").with(Language.EN, "Soup of the Day"),
        LocalizedContent.of(Language.PT, "Sabor especial do chef")
            .with(Language.EN, "Chef's special flavor"));

    // NEW Offering Dinner - Starters
    createOfferingUseCase.execute(
        business.getId(),
        catalogDinner.getId(),
        categoryDinnerStarters.getId(),
        BigDecimal.valueOf(5.00),
        LocalizedContent.of(Language.PT, "Salada Verde").with(Language.EN, "Green Salad"),
        LocalizedContent.of(Language.PT, "Alface, rúcula e tomate")
            .with(Language.EN, "Lettuce, arugula, and tomato"));

    // Offerings Dinner - Main Courses
    createOfferingUseCase.execute(
        business.getId(),
        catalogDinner.getId(),
        categoryDinnerMain.getId(),
        BigDecimal.valueOf(18.00),
        LocalizedContent.of(Language.PT, "Bacalhau à Brás").with(Language.EN, "Codfish à Brás"),
        LocalizedContent.of(Language.PT, "Bacalhau desfiado com batata palha")
            .with(Language.EN, "Shredded cod with shoestring potatoes"));

    createOfferingUseCase.execute(
        business.getId(),
        catalogDinner.getId(),
        categoryDinnerMain.getId(),
        BigDecimal.valueOf(20.00),
        LocalizedContent.of(Language.PT, "Robalo Grelhado").with(Language.EN, "Grilled Sea Bass"),
        LocalizedContent.of(Language.PT, "Robalo com legumes frescos")
            .with(Language.EN, "Sea bass with fresh vegetables"));

    // NEW Offering Dinner - Main Courses
    createOfferingUseCase.execute(
        business.getId(),
        catalogDinner.getId(),
        categoryDinnerMain.getId(),
        BigDecimal.valueOf(19.50),
        LocalizedContent.of(Language.PT, "Frango ao Molho").with(Language.EN, "Chicken with Sauce"),
        LocalizedContent.of(Language.PT, "Frango grelhado com molho especial")
            .with(Language.EN, "Grilled chicken with special sauce"));

    log.info(
        String.format(
            "Demo business '%s' created, alias %s", business.getName(), business.getAlias()));
  }
}
