package vitor.tinelli.service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vitor.tinelli.controller.SelicController;
import vitor.tinelli.domain.Nfe;
import vitor.tinelli.domain.NfeTax;
import vitor.tinelli.domain.Taxes;
import vitor.tinelli.exceptions.BadRequestException;
import vitor.tinelli.repository.NfeTaxRepository;
import vitor.tinelli.requests.DateGapRequestBody;
import vitor.tinelli.requests.NfeTaxYearMonthRequestBody;

@Service
@RequiredArgsConstructor
public class NfeTaxService {

  private final NfeTaxRepository nfeTaxRepository;
  private final NfeService nfeService;
  private final TaxesService taxesService;
  private final SelicController selicController;
  private final List<NfeTax> newNfeTaxList = new ArrayList<>();

  public List<NfeTax> listAll() {
    return nfeTaxRepository.findAll();
  }

  public List<NfeTax> listByNfeID(UUID id) {
    return nfeTaxRepository.findByNfe(nfeService.findByIdOrThrowBadRequestException(id));
  }

  public ResponseEntity<List<NfeTax>> getByNfeMonthAndYear(
      NfeTaxYearMonthRequestBody nfeTaxYearMonthRequestBody) {
    return ResponseEntity.ok(
        nfeTaxRepository.findByMonthAndYear(nfeTaxYearMonthRequestBody.getMonth(),
            nfeTaxYearMonthRequestBody.getYear()));
  }

  @Transactional
  public List<NfeTax> postEveryNfeWithoutTax() {
    newNfeTaxList.clear();
    List<Nfe> nfeList = sortNfeTaxListOrThrowBadRequestIfNfeNotExist();
    List<Taxes> taxesList = taxesService.listAll();

    nfeList.forEach(nfe -> taxesList.forEach(taxes -> {
      if (nfeTaxRepository.findByNfeAndTaxes(nfe, taxes).isEmpty()) {
        NfeTax nfeTax = calculateTaxAndBuildNfeTax(nfe, taxes);
        newNfeTaxList.add(nfeTax);
      }
    }));
    nfeTaxRepository.saveAll(newNfeTaxList);
    return newNfeTaxList;
  }

  public List<NfeTax> postEveryNfeWithoutTaxByDate(DateGapRequestBody dateGapRequestBody) {
    newNfeTaxList.clear();
    List<Nfe> nfeList = nfeService.findByDateGap(dateGapRequestBody.getStartDate(),
        dateGapRequestBody.getEndDate());
    if (nfeList.isEmpty()) {
      throw new BadRequestException("No NFEs found in the provided date range");
    }
    nfeList.sort(Comparator.comparing(Nfe::getDate));
    List<Taxes> taxesList = taxesService.listAll();

    nfeList.forEach(nfe -> taxesList.forEach(taxes -> {
      if (nfeTaxRepository.findByNfeAndTaxes(nfe, taxes).isEmpty()) {
        NfeTax nfeTax = calculateTaxAndBuildNfeTax(nfe, taxes);
        newNfeTaxList.add(nfeTax);
      }
    }));
    nfeTaxRepository.saveAll(newNfeTaxList);
    return newNfeTaxList;
  }

  private List<Nfe> sortNfeTaxListOrThrowBadRequestIfNfeNotExist() {
    List<Nfe> nfeList = nfeService.listAll();
    if (nfeList.isEmpty()) {
      throw new BadRequestException("No NFEs found");
    }
    nfeList.sort(Comparator.comparing(Nfe::getDate));
    return nfeList;
  }

  private NfeTax calculateTaxAndBuildNfeTax(Nfe nfe, Taxes taxes) {
    Double difference =
        nfe.getValue() * ((taxes.getAliquot() + selicController.getSelicAliquotPerMonth()) / 100);
    Double taxedValue = nfe.getValue() + difference;
    DecimalFormat formatter = new DecimalFormat("0.00");

    return NfeTax.builder()
        .nfe(nfe)
        .taxes(taxes)
        .valueWithTax(Double.parseDouble(formatter.format(taxedValue)))
        .difference(Double.parseDouble(formatter.format(difference)))
        .month(nfe.getDate().getMonth().toString())
        .year(nfe.getDate().getYear())
        .build();
  }

  public ResponseEntity<List<NfeTax>> getByNfeYear(Integer year) {
    return ResponseEntity.ok(nfeTaxRepository.findByYear(year));
  }

  public void deleteById(UUID id) {
    nfeTaxRepository.delete(nfeTaxRepository.findById(id).orElseThrow(
        () -> new BadRequestException("NfeTax not found, please verify the provided ID")));

  }

  public void deleteByNfeId(UUID id) {
    nfeTaxRepository.deleteAll(
        nfeTaxRepository.findByNfe(nfeService.findByIdOrThrowBadRequestException(id)));
  }
}
