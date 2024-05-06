package onboardingMarcos.tinelli.service;


import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.toList;
import java.math.BigDecimal;
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

  private final DecimalFormat formatter = new DecimalFormat("0.00");
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
    return taxCalculationRepository.findAll().stream().map(tax -> {
      tax.setTaxedValue(new BigDecimal(formatter.format(tax.getTaxedValue())));
      tax.setNfeValue(new BigDecimal(formatter.format(tax.getNfeValue())));
      return tax;
    }).collect(toList());
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
    List<TaxCalculation> newTaxedValues = new ArrayList<>();
    List<Taxes> taxesList = taxesService.listAll();
    List<TaxCalculation> savedTaxes = taxCalculationRepository.findByCalculationDate(
        dateRequestBody.with(firstDayOfMonth()));
    List<Nfe> nfeList = nfeService.findByTimeGap(dateRequestBody.with(firstDayOfMonth()),
        dateRequestBody.with((lastDayOfMonth())));
    Double nfeValue = nfeList.stream().mapToDouble(Nfe::getValue).sum();

    if (savedTaxes.isEmpty()) {
      taxesList.forEach(
          tax -> newTaxedValues.add(
              calculateTaxAndSave(tax, nfeValue, dateRequestBody)));
    } else if (savedTaxes.size() != taxesList.size()
        || savedTaxes.get(0).getNfeValue().compareTo(BigDecimal.valueOf(nfeValue)) != 0) {
      taxCalculationRepository.deleteAll(savedTaxes);
      taxesList.forEach(
          tax -> newTaxedValues.add(
              calculateTaxAndSave(tax, nfeValue, dateRequestBody)));
    } else {
      throw new BadRequestException("Already calculated for this period.");
    }
    return newTaxedValues;
  }

  private TaxCalculation calculateTaxAndSave(Taxes tax, Double nfeValue, LocalDate date) {
    Double taxedValue = nfeValue * ((tax.getAliquot() + selicService.getSelicPerMonth()) / 100);
    return taxCalculationRepository.save(
        new TaxCalculation(UUID.randomUUID(),
            BigDecimal.valueOf(Double.parseDouble(formatter.format(nfeValue))),
            BigDecimal.valueOf(Double.parseDouble(formatter.format(taxedValue))),
            date.with(firstDayOfMonth()), tax));
  }

  public void deleteById(UUID id) {
    taxCalculationRepository.delete(findByIdOrThrowBadExceptionError(id));
  }

  public void deleteAllByMonth(LocalDate date) {
    taxCalculationRepository.deleteAll(findByMonth(date));
  }
}