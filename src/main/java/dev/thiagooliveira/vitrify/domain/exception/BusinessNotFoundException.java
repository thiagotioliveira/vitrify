package dev.thiagooliveira.vitrify.domain.exception;

public class BusinessNotFoundException extends DomainException {

  public BusinessNotFoundException() {
    super("Business not found");
  }
}
