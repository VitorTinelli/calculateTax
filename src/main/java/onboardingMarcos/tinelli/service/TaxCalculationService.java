package onboardingMarcos.tinelli.service;


import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.TaxCalculation;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.TaxCalculationRepository;
import org.springframework.stereotype.Service;

@Service
public class TaxCalculationService {

  private final SelicService selicService;
  private final TaxCalculationRepository taxCalculationRepository;
  private final NfeService nfeService;
  private final TaxesService taxesService;

  public TaxCalculationService(final SelicService selicService,
      final TaxCalculationRepository taxCalculationRepository,
      final NfeService nfeService, final TaxesService taxesService) {
    this.selicService = selicService;
    this.taxCalculationRepository = taxCalculationRepository;
    this.nfeService = nfeService;
    this.taxesService = taxesService;
  }

  public List<TaxCalculation> listAll() {
    return taxCalculationRepository.findAll();
  }

  public TaxCalculation findByIdOrThrowBadExceptionError(UUID id) {
    return taxCalculationRepository.findById(id)
        .orElseThrow(
            () -> new BadRequestException("Tax not found, please verify the provided ID."));
  }

  public List<TaxCalculation> findByMonth(LocalDate date) {
    List<TaxCalculation> list =
        taxCalculationRepository.findByCalculationDate(date.with(firstDayOfMonth()));
    if (list.isEmpty()) {
      throw new BadRequestException("No taxes calculated for this month.");
    } else {
      return list;
    }
  }

  public List<TaxCalculation> postByDatePeriod(LocalDate dateRequestBody) {
    double totalValue;
    List<TaxCalculation> newTaxedValues = new ArrayList<>();
    List<Taxes> taxesList = taxesService.listAll();
    List<TaxCalculation> savedTaxes = taxCalculationRepository.findByCalculationDate(
        dateRequestBody.with(firstDayOfMonth()));
    List<Nfe> nfeList = nfeService.findByTimeGap(dateRequestBody.with(firstDayOfMonth()),
        dateRequestBody.with((lastDayOfMonth())));
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
    double taxedValue =
        totalValue * ((tax.getAliquot() + selicService.getSelicPerMonth()) / 100);
    DecimalFormat formatter = new DecimalFormat("0.00");
    taxedValue = Double.parseDouble(formatter.format(taxedValue));
    return taxCalculationRepository.save(
        new TaxCalculation(UUID.randomUUID(), totalValue, taxedValue,
            date.with(firstDayOfMonth()), tax));
  }

  public void deleteById(UUID id) {
    taxCalculationRepository.delete(findByIdOrThrowBadExceptionError(id));
  }

  public void deleteAllByMonth(LocalDate date) {
    taxCalculationRepository.deleteAll(findByMonth(date));
  }
}