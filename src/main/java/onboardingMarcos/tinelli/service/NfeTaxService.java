package onboardingMarcos.tinelli.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.controller.SelicController;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.NfeTax;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.NfeTaxRepository;
import onboardingMarcos.tinelli.requests.NfeTaxYearMonthRequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public ResponseEntity<List<NfeTax>> getByNfeYear(Long year) {
    return ResponseEntity.ok(nfeTaxRepository.findByYear(year));
  }

  public ResponseEntity<NfeTax> findByIdOrThrowBadRequestException(UUID id) {
    NfeTax nfeTax = nfeTaxRepository.findById(id).orElseThrow(
        () -> new BadRequestException("NfeTax not found, please verify the provided ID"));
    return ResponseEntity.ok(nfeTax);
  }

  public ResponseEntity<List<NfeTax>> getByNfeMonthAndYear(
      NfeTaxYearMonthRequestBody nfeTaxYearMonthRequestBody) {
    return ResponseEntity.ok(
        nfeTaxRepository.findByMonthAndYear(nfeTaxYearMonthRequestBody.getMonth(),
            nfeTaxYearMonthRequestBody.getYear()));
  }

  @Transactional
  public ResponseEntity<List<NfeTax>> postEveryNfeWithoutTax() {
    newNfeTaxList.clear();
    try {
      List<Nfe> sortedNfeList = getNfeListOrThrowBadRequestException();
      List<Taxes> taxesList = taxesService.listAll();

      sortedNfeList.forEach(nfe -> taxesList.forEach(tax -> {
        if (nfeTaxRepository.findByNfeAndTaxes(nfe, tax).isPresent()) {
          NfeTax savedNFE = nfeTaxRepository.save(calculateTaxAndCreateConstructor(nfe, tax));
          newNfeTaxList.add(savedNFE);
        }
      }));
    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
    return ResponseEntity.ok(newNfeTaxList);
  }

  @Transactional
  public ResponseEntity<List<NfeTax>> postEveryNfeWithoutTaxByDateGap(LocalDate start,
      LocalDate end) {
    newNfeTaxList.clear();
    try {
      List<Nfe> nfeList = nfeService.findByTimeGap(start, end);
      if (!nfeList.isEmpty()) {
        nfeList.sort(Comparator.comparing(Nfe::getDate));
      } else {
        throw new BadRequestException("No NFEs found");
      }
      List<Taxes> taxesList = taxesService.listAll();

      nfeList.forEach(nfe -> taxesList.forEach(tax -> {
        if (nfeTaxRepository.findByNfeAndTaxes(nfe, tax).isEmpty()) {
          NfeTax savedNFE = nfeTaxRepository.save(calculateTaxAndCreateConstructor(nfe, tax));
          newNfeTaxList.add(savedNFE);
        }
      }));

    } catch (Exception exception) {
      throw new BadRequestException(exception.getMessage());
    }
    return ResponseEntity.ok(newNfeTaxList);
  }

  public List<Nfe> getNfeListOrThrowBadRequestException() {
    List<Nfe> nfeList = nfeService.listAll();
    if (!nfeList.isEmpty()) {
      nfeList.sort(Comparator.comparing(Nfe::getDate));
    } else {
      throw new BadRequestException("No NFEs found");
    }
    return nfeList;
  }

  public NfeTax calculateTaxAndCreateConstructor(Nfe nfe, Taxes taxes) {
    Double difference =
        nfe.getValue() * ((taxes.getAliquot() + selicController.getSelicPerMonth()) / 100);
    Double taxedValue = nfe.getValue() + difference;
    DecimalFormat formatter = new DecimalFormat("0.00");

    return new NfeTax(UUID.randomUUID(), nfe, taxes,
        Double.parseDouble(formatter.format(taxedValue)),
        Double.parseDouble(formatter.format(taxedValue - nfe.getValue())),
        nfe.getDate().getMonth().toString(), nfe.getDate().getYear());
  }

  public void deleteAllByNfeID(UUID id) {
    Nfe nfe = nfeService.findByIdOrThrowBadRequestException(id);
    List<NfeTax> nfeTax = nfeTaxRepository.findByNfe(nfe);
    if (nfeTax.isEmpty()) {
      throw new BadRequestException("No registered NfeTax with this NFE");
    }
    nfeTaxRepository.deleteAll(nfeTax);
  }

  public void deleteByNfeTaxIdOrThrowBadRequestException(UUID id) {
    findByIdOrThrowBadRequestException(id);
    nfeTaxRepository.deleteById(id);
  }
}



