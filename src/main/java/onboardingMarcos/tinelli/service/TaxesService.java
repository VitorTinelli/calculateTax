package onboardingMarcos.tinelli.service;


import java.util.List;
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

  public Taxes findByIdOrThrowBadRequestException(Long id) {
    return taxesRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "Taxes not Found, Please verify the provided ID"));
  }

  public Long lastTaxesID() {
    List<Taxes> taxes = taxesRepository.findAll();
    Taxes lastTaxes = taxes.get(taxes.size() - 1);
    return lastTaxes.getId();
  }

  public Taxes save(TaxesPostRequestBody taxesPostRequestBody) {
    Verifications.verificationTaxesPOST(taxesPostRequestBody);
    return taxesRepository.save(
        new Taxes(
            lastTaxesID() + 1,
            taxesPostRequestBody.getName(),
            taxesPostRequestBody.getAliquot()
        )
    );
  }

  public void delete(Long id) {
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
