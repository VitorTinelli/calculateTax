package onboardingMarcos.tinelli.service;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import onboardingMarcos.tinelli.controller.SelicController;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.TaxCalculation;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.TaxCalculationRepository;
import onboardingMarcos.tinelli.util.FirstAndLastDayOfMonth;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TaxCalculationService {

  private final SelicController selicController;
  private final TaxCalculationRepository taxCalculationRepository;
  private final NfeService nfeService;
  private final TaxesService taxesService;

  public TaxCalculationService(final SelicController selicController,
      final TaxCalculationRepository taxCalculationRepository,
      final NfeService nfeService, final TaxesService taxesService) {
    this.selicController = selicController;
    this.taxCalculationRepository = taxCalculationRepository;
    this.nfeService = nfeService;
    this.taxesService = taxesService;
  }

  public List<TaxCalculation> ListAll() {
    return taxCalculationRepository.findAll();
  }

  public TaxCalculation findByIdOrThrowBadExceptionError(UUID id) {
    return taxCalculationRepository.findById(id)
        .orElseThrow(
            () -> new BadRequestException("Tax not found, please verify the provided ID."));
  }

  public List<TaxCalculation> postByDatePeriod(LocalDate dateRequestBody) {
    double totalValue;
    List<TaxCalculation> newTaxedValues = new ArrayList<>();
    List<Taxes> taxesList = taxesService.listAll();
    List<TaxCalculation> savedTaxes = taxCalculationRepository.findByCalculationDate(
        FirstAndLastDayOfMonth.firstDay(dateRequestBody));
    List<Nfe> nfeList = nfeService.findByTimeGap(FirstAndLastDayOfMonth.firstDay(dateRequestBody),
        FirstAndLastDayOfMonth.lastDay(dateRequestBody));
    totalValue = nfeList.stream().mapToDouble(Nfe::getValue).sum();

    if (savedTaxes.isEmpty()) {
      taxesList.forEach(
          tax -> newTaxedValues.add(calculateTaxAndSave(tax, totalValue, dateRequestBody)));
    } else if (savedTaxes.size() != taxesList.size()
        || savedTaxes.get(0).getTotalValue() != totalValue) {
      taxCalculationRepository.deleteAll(savedTaxes);
      taxesList.forEach(
          tax -> newTaxedValues.add(calculateTaxAndSave(tax, totalValue, dateRequestBody)));
    } else {
      throw new BadRequestException("Already calculated for this period.");
    }
    return newTaxedValues;
  }

  private TaxCalculation calculateTaxAndSave(Taxes tax, double totalValue, LocalDate date) {
    double difference =
        totalValue * ((tax.getAliquot() + selicController.getSelicPerMonth()) / 100);
    Double taxedValue = totalValue + difference;
    DecimalFormat formatter = new DecimalFormat("0.00");
    taxedValue = Double.parseDouble(formatter.format(taxedValue));
    return taxCalculationRepository.save(
        new TaxCalculation(UUID.randomUUID(), totalValue, taxedValue,
            FirstAndLastDayOfMonth.firstDay(date), tax));
  }

  public void deleteById(UUID id) {
    taxCalculationRepository.delete(findByIdOrThrowBadExceptionError(id));
  }
}