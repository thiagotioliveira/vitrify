package dev.thiagooliveira.vitrify.domain.exception;

public class CatalogNotFoundException extends DomainException {

  public CatalogNotFoundException() {
    super("Catalog not found");
  }
}
