package onboardingMarcos.tinelli.service;


import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.domain.TaxedPeriod;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.TaxedPeriodRepository;
import org.springframework.stereotype.Service;

@Service
public class TaxedPeriodService {

  private final DecimalFormat formatter = new DecimalFormat("0.00");
  private final SelicService selicService;
  private final TaxedPeriodRepository taxedPeriodRepository;
  private final NfeService nfeService;
  private final TaxesService taxesService;

  public TaxedPeriodService(final SelicService selicService,
      final TaxedPeriodRepository taxedPeriodRepository,
      final NfeService nfeService, final TaxesService taxesService) {
    this.selicService = selicService;
    this.taxedPeriodRepository = taxedPeriodRepository;
    this.nfeService = nfeService;
    this.taxesService = taxesService;
  }

  public List<TaxedPeriod> listAll() {
    return taxedPeriodRepository.findAll();
  }

  public TaxedPeriod findByIdOrThrowBadExceptionError(UUID id) {
    return taxedPeriodRepository.findById(id)
        .orElseThrow(
            () -> new BadRequestException("Tax not found, please verify the provided ID."));
  }

  public List<TaxedPeriod> findByMonth(LocalDate date) {
    List<TaxedPeriod> list =
        taxedPeriodRepository.findByCalculationDate(date.with(firstDayOfMonth()));
    if (list.isEmpty()) {
      throw new BadRequestException("No taxes calculated for this month.");
    } else {
      return list;
    }
  }

  public List<TaxedPeriod> postByDatePeriod(LocalDate dateRequestBody) {
    List<TaxedPeriod> newTaxedValues = new ArrayList<>();
    List<Taxes> taxesList = taxesService.listAll();
    List<TaxedPeriod> savedTaxes = taxedPeriodRepository.findByCalculationDate(
        dateRequestBody.with(firstDayOfMonth()));
    List<Nfe> nfeList = nfeService.findByTimePeriod(dateRequestBody.with(firstDayOfMonth()),
        dateRequestBody.with((lastDayOfMonth())));
    Double nfeValue = nfeList.stream().mapToDouble(Nfe::getValue).sum();

    if (savedTaxes.isEmpty()) {
      taxesList.forEach(
          tax -> newTaxedValues.add(
              calculateTaxAndSave(tax, nfeValue, dateRequestBody)));
    } else if (savedTaxes.size() != taxesList.size()
        || savedTaxes.get(0).getNfeValue().compareTo(BigDecimal.valueOf(nfeValue)) != 0) {
      taxedPeriodRepository.deleteAll(savedTaxes);
      taxesList.forEach(
          tax -> newTaxedValues.add(
              calculateTaxAndSave(tax, nfeValue, dateRequestBody)));
    } else {
      throw new BadRequestException("Already calculated for this period.");
    }
    return newTaxedValues;
  }

  private TaxedPeriod calculateTaxAndSave(Taxes tax, Double nfeValue, LocalDate date) {
    Double taxedValue = nfeValue * ((tax.getAliquot() + selicService.getSelicPerMonth()) / 100);
    return taxedPeriodRepository.save(
        new TaxedPeriod(UUID.randomUUID(),
            BigDecimal.valueOf(Double.parseDouble(formatter.format(nfeValue))),
            BigDecimal.valueOf(Double.parseDouble(formatter.format(taxedValue))),
            date.with(firstDayOfMonth()), tax));
  }

  public void deleteById(UUID id) {
    taxedPeriodRepository.delete(findByIdOrThrowBadExceptionError(id));
  }

  public void deleteAllByMonth(LocalDate date) {
    taxedPeriodRepository.deleteAll(findByMonth(date));
  }
}