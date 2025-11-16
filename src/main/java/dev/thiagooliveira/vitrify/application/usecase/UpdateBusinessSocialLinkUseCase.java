package dev.thiagooliveira.vitrify.application.usecase;

import dev.thiagooliveira.vitrify.domain.exception.BusinessNotFoundException;
import dev.thiagooliveira.vitrify.domain.model.Business;
import dev.thiagooliveira.vitrify.domain.model.SocialLink;
import dev.thiagooliveira.vitrify.domain.repository.BusinessRepository;
import java.util.UUID;

public class UpdateBusinessSocialLinkUseCase {

  private final BusinessRepository businessRepository;

  public UpdateBusinessSocialLinkUseCase(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Business execute(UUID businessId, SocialLink link) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    business.addLink(link);
    this.businessRepository.save(business);
    return business;
  }
}
