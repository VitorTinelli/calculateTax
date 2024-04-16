package vitor.tinelli.service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vitor.tinelli.controller.SelicController;
import vitor.tinelli.domain.Nfe;
import vitor.tinelli.domain.NfeTax;
import vitor.tinelli.domain.Taxes;
import vitor.tinelli.exceptions.BadRequestException;
import vitor.tinelli.repository.NfeTaxRepository;

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

  @Transactional
  public List<NfeTax> postEveryNfeWithoutTax() {
    List<Nfe> nfeList = sortListOrThrowBadRequestIfNfeNotExist();
    List<Taxes> taxesList = taxesService.listAll();

    for (Nfe nfe : nfeList) {
      for (Taxes taxes : taxesList) {
        if (nfeTaxRepository.findByNfeAndTaxes(nfe, taxes).isPresent()) {
          continue;
        }
        NfeTax nfeTax = calculateTaxAndBuildNfeTax(nfe, taxes);
        newNfeTaxList.add(nfeTax);
      }
    }
    nfeTaxRepository.saveAll(newNfeTaxList);
    return newNfeTaxList;
  }

  private List<Nfe> sortListOrThrowBadRequestIfNfeNotExist() {
    List<Nfe> nfeList = nfeService.listAll();
    if (nfeList.isEmpty()) {
      throw new BadRequestException("No NFEs found");
    }
    nfeList.sort(Comparator.comparing(Nfe::getDate));
    return nfeList;
  }

  private NfeTax calculateTaxAndBuildNfeTax(Nfe nfe, Taxes taxes) {
    Double difference = nfe.getValue() * ((taxes.getAliquot() + selicController.getSelicAliquotPerMonth()) / 100);
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
}
