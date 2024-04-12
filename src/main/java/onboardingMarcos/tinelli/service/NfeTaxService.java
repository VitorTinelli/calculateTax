package onboardingMarcos.tinelli.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import onboardingMarcos.tinelli.controller.SelicController;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeTaxRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NfeTaxService {

  private final NfeTaxRepository nfeTaxRepository;
  private final NfeService nfeService;
  private final TaxesService taxesService;
  private final SelicController selicController;
  private final List<NfeTax> newNfeTaxList = new ArrayList<>();

  public NfeTaxService(NfeTaxRepository nfeTaxRepository, NfeService nfeService,
      TaxesService taxesService, SelicController selicController) {
    this.nfeTaxRepository = nfeTaxRepository;
    this.nfeService = nfeService;
    this.taxesService = taxesService;
    this.selicController = selicController;
  }

  public ResponseEntity<List<NfeTax>> listAll() {
    return ResponseEntity.ok(nfeTaxRepository.findAll());
  }

  public ResponseEntity<List<NfeTax>> getByNfeId(String uuid) {
    return ResponseEntity.ok(nfeTaxRepository.findByNfe(
        nfeService.findByIdOrThrowBadRequestException(UUID.fromString(uuid))));
  }

  public ResponseEntity<List<NfeTax>> postEveryNfeWithoutTax() {
    try {
      List<Nfe> nfeList = nfeService.listAll();
      List<Taxes> taxesList = taxesService.listAll();
      DecimalFormat formatter = new DecimalFormat("0.00");

      if (!nfeList.isEmpty()) {
        nfeList.sort(Comparator.comparing(Nfe::getDate));
      } else {
        throw new BadRequestException("No NFEs found");
      }

      for (Nfe nfe : nfeList) {
        for (Taxes taxes : taxesList) {
          if (nfeTaxRepository.findByNfeAndTaxes(nfe, taxes).isPresent()) {
            continue;
          }
          Double difference =
              nfe.getValue() * ((taxes.getAliquot() + selicController.getSelicPerMonth()) / 100);
          Double taxedValue = nfe.getValue() + difference;

          NfeTax savedNFE = nfeTaxRepository.save(
              new NfeTax(UUID.randomUUID(), nfe, taxes,
                  Double.parseDouble(formatter.format(taxedValue)),
                  Double.parseDouble(formatter.format(taxedValue - nfe.getValue())),
                  nfe.getDate().getMonth().toString(), nfe.getDate().getYear()));
          newNfeTaxList.add(savedNFE);
        }
      }
    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
    return ResponseEntity.ok(newNfeTaxList);
  }
}

