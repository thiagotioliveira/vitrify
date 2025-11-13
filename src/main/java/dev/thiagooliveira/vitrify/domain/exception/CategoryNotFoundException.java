package dev.thiagooliveira.vitrify.domain.exception;

public class CategoryNotFoundException extends DomainException {

  public CategoryNotFoundException() {
    super("Category not found");
  }
}
