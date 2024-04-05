package onboardingMarcos.tinelli.service;


import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.TaxesRepository;
import onboardingMarcos.tinelli.requests.TaxesPostRequestBody;
import onboardingMarcos.tinelli.requests.TaxesPutRequestBody;
import onboardingMarcos.tinelli.util.Verifications;
import org.springframework.stereotype.Service;

@Service
public class TaxesService {

  private final TaxesRepository taxesRepository;

  public TaxesService(TaxesRepository taxesRepository) {
    this.taxesRepository = taxesRepository;
  }

  public List<Taxes> listAll() {
    return taxesRepository.findAll();
  }

  public Taxes findByIdOrThrowBadRequestException(UUID id) {
    return taxesRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "Taxes not Found, Please verify the provided ID"));
  }

  public Taxes save(TaxesPostRequestBody taxesPostRequestBody) {
    Verifications.verificationTaxesPOST(taxesPostRequestBody);
    return taxesRepository.save(
        new Taxes(
            UUID.randomUUID(),
            taxesPostRequestBody.getName(),
            taxesPostRequestBody.getAliquot()
        )
    );
  }

  public void delete(UUID id) {
    findByIdOrThrowBadRequestException(id);
    taxesRepository.deleteById(id);
  }

  public void replace(TaxesPutRequestBody taxesPutRequestBody) {
    Taxes savedTaxes = findByIdOrThrowBadRequestException(taxesPutRequestBody.getId());
    Verifications.verificationTaxesPUT(taxesPutRequestBody);
    taxesRepository.save(
        new Taxes(
            savedTaxes.getId(),
            taxesPutRequestBody.getName(),
            taxesPutRequestBody.getAliquot()
        )
    );
  }
}
