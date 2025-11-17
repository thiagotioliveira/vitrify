package dev.thiagooliveira.vitrify.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.vitrify.domain.exception.DomainException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessTest {
  private static final String PADARIA_PORTUGUESA = "Padaria Portuguesa";
  private static final String PADARIA_PORTUGUESA_ALIAS = "padaria-portuguesa";
  private static final String NEW_NAME = "new name";
  private static final String ENTRADAS = "Entradas";
  private static final String STARTERS = "Starters";
  private static final String SOPAS = "Sopas";
  private static final String SOUPS = "Soups";
  private static final String SOPA_DO_DIA = "Sopa do dia";
  private static final String SOUP_OF_THE_DAY = "Soup of the day";
  private static final String SOPA_CASEIRA = "Sopa caseira";
  private static final String HOMEMADE_SOUP = "Homemade soup";

  private String name;
  private Set<Language> supportedLanguages;

  @BeforeEach
  void setUp() {
    this.name = PADARIA_PORTUGUESA;
    this.supportedLanguages = Set.of(Language.PT, Language.EN);
  }

  @Test
  void shouldCreate() {
    var business = Business.create(name, supportedLanguages);

    assertNotNull(business);
    assertNotNull(business.getId());
    assertEquals(name, business.getName());
    assertEquals(PADARIA_PORTUGUESA_ALIAS, business.getAlias());
    assertEquals(supportedLanguages, business.getSupportedLanguages());
    assertTrue(business.getCatalogs().isEmpty());
  }

  @Test
  void shouldUpdate() {
    var business = Business.create(name, supportedLanguages);
    var newSupportedLanguage = Set.of(Language.PT);
    business.update(NEW_NAME, null, newSupportedLanguage);
    assertEquals(NEW_NAME, business.getName());
    assertEquals(PADARIA_PORTUGUESA_ALIAS, business.getAlias());
    assertEquals(newSupportedLanguage, business.getSupportedLanguages());
  }

  @Test
  void shouldAddCatalog() {
    var business = Business.create(name, supportedLanguages);
    var catalog =
        Catalog.create(LocalizedContent.of(Language.PT, ENTRADAS).with(Language.EN, STARTERS));
    business.addCatalog(catalog);
    assertTrue(business.getCatalogs().contains(catalog));
  }

  @Test
  void shouldThrowExceptionWhenAddingCatalogWithIncompleteLocalizedContent() {
    var business = Business.create(name, supportedLanguages);
    var catalog = Catalog.create(LocalizedContent.of(Language.PT, ENTRADAS));
    var ex = assertThrows(DomainException.class, () -> business.addCatalog(catalog));
  }

  @Test
  void shouldAddCategory() {
    var business = Business.create(name, supportedLanguages);
    var catalog =
        Catalog.create(LocalizedContent.of(Language.PT, ENTRADAS).with(Language.EN, STARTERS));
    business.addCatalog(catalog);

    var category =
        Category.create(LocalizedContent.of(Language.PT, SOPAS).with(Language.EN, SOUPS));
    catalog.addCategory(category);
    assertTrue(catalog.getCategories().contains(category));
  }

  @Test
  void shouldThrowExceptionWhenAddingCategoryWithIncompleteLocalizedContent() {
    var business = Business.create(name, supportedLanguages);
    var catalog =
        Catalog.create(LocalizedContent.of(Language.PT, ENTRADAS).with(Language.EN, STARTERS));
    business.addCatalog(catalog);

    var category = Category.create(LocalizedContent.of(Language.PT, SOPAS));
    var ex =
        assertThrows(DomainException.class, () -> business.addCategory(catalog.getId(), category));
  }

  @Test
  void shouldAddOffering() {
    var business = Business.create(name, supportedLanguages);
    var catalog =
        Catalog.create(LocalizedContent.of(Language.PT, ENTRADAS).with(Language.EN, STARTERS));
    business.addCatalog(catalog);

    var category =
        Category.create(LocalizedContent.of(Language.PT, SOPAS).with(Language.EN, SOUPS));

    business.addCategory(catalog.getId(), category);

    var offering =
        Offering.create(
            BigDecimal.TEN,
            LocalizedContent.of(Language.PT, SOPA_DO_DIA).with(Language.EN, SOUP_OF_THE_DAY),
            LocalizedContent.of(Language.PT, SOPA_CASEIRA).with(Language.EN, HOMEMADE_SOUP),
            List.of("mock.png"));
    business.addOffering(category.getId(), offering);
    assertTrue(category.getOfferings().contains(offering));
  }

  @Test
  void shouldThrowExceptionWhenAddingOfferingWithIncompleteLocalizedContent() {
    var business = Business.create(name, supportedLanguages);
    var catalog =
        Catalog.create(LocalizedContent.of(Language.PT, ENTRADAS).with(Language.EN, STARTERS));
    business.addCatalog(catalog);

    var category =
        Category.create(LocalizedContent.of(Language.PT, SOPAS).with(Language.EN, SOUPS));
    business.addCategory(catalog.getId(), category);

    var offering =
        Offering.create(
            BigDecimal.TEN,
            LocalizedContent.of(Language.PT, SOPA_DO_DIA),
            LocalizedContent.of(Language.PT, SOPA_CASEIRA).with(Language.EN, HOMEMADE_SOUP),
            List.of("mock.png"));
    var ex =
        assertThrows(DomainException.class, () -> business.addOffering(category.getId(), offering));
  }
}
