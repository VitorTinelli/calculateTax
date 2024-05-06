package onboardingMarcos.tinelli.services;

import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxCalculation;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.TaxCalculationRepository;
import onboardingMarcos.tinelli.requests.DateRequestBody;
import onboardingMarcos.tinelli.service.NfeService;
import onboardingMarcos.tinelli.service.SelicService;
import onboardingMarcos.tinelli.service.TaxCalculationService;
import onboardingMarcos.tinelli.service.TaxesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxCalculationServiceTest {

  UUID id;
  TaxCalculation taxCalculation;
  Taxes taxes;
  DateRequestBody dateRequestBody;

  @InjectMocks
  private TaxCalculationService taxCalculationService;

  @Mock
  private TaxCalculationRepository taxCalculationRepository;

  @Mock
  private NfeService nfeService;

  @Mock
  private TaxesService taxesService;

  @Mock
  private SelicService selicService;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    taxes = new Taxes(id, "ISS", 10);
    taxCalculation = new TaxCalculation(id, BigDecimal.valueOf(300D), BigDecimal.valueOf(200D),
        LocalDate.now(), taxes);
    dateRequestBody = new DateRequestBody(LocalDate.now());
  }

  @Test
  @DisplayName("List all return all calculated months")
  void listAll_returnAllCalculatedMonths_WhenSuccessful() {
    when(taxCalculationService.listAll()).thenReturn(
        List.of(taxCalculation));
    Assertions.assertFalse(taxCalculationService.listAll().isEmpty());
    verify(taxCalculationRepository).findAll();
    verifyNoMoreInteractions(taxCalculationRepository);
  }

  @Test
  @DisplayName("Find by taxCalculated ID return a taxCalculated (one tax)")
  void findByTaxCalculatedId_returnTaxCalculated_WhenSuccessful() {
    when(taxCalculationRepository.findById(id)).thenReturn(Optional.of(taxCalculation));
    Assertions.assertEquals(taxCalculation,
        taxCalculationService.findByIdOrThrowBadExceptionError(id));
    verify(taxCalculationRepository).findById(id);
    verifyNoMoreInteractions(taxCalculationRepository);
  }

  @Test
  @DisplayName("Find by taxCalculated ID throw BadRequestException when taxCalculated not found")
  void findByTaxCalculatedId_throwBadRequestException_WhenTaxCalculatedNotFound() {
    when(taxCalculationRepository.findById(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class,
        () -> taxCalculationService.findByIdOrThrowBadExceptionError(id));
  }

  @Test
  @DisplayName("Find by month return a taxCalculated list when successful")
  void findByMonth_returnTaxCalculatedList_WhenSuccessful() {
    when(taxCalculationRepository.findByCalculationDate(any(LocalDate.class)))
        .thenReturn(List.of(taxCalculation));
    Assertions.assertFalse(taxCalculationService.findByMonth(dateRequestBody.getDate()).isEmpty());
    verifyNoMoreInteractions(taxCalculationRepository);
  }

  @Test
  @DisplayName("Find by month throw BadRequestException when no taxCalculated found")
  void findByMonth_throwBadRequestException_WhenNoTaxCalculatedFound() {
    when(taxCalculationRepository.findByCalculationDate(any(LocalDate.class))).thenReturn(
        List.of());
    Assertions.assertThrows(BadRequestException.class,
        () -> taxCalculationService.findByMonth(dateRequestBody.getDate()));
  }

  @Test
  @DisplayName("Post by date period return a taxCalculated if any nfe found")
  void postByDatePeriod_returnTaxCalculated_WhenAnyNfeFound() {
    when(nfeService.findByTimeGap(any(LocalDate.class), any(LocalDate.class))).thenReturn(
        List.of());
    when(taxesService.listAll()).thenReturn(List.of(taxes));
    when(taxCalculationRepository.save(any(TaxCalculation.class))).thenReturn(taxCalculation);
    when(selicService.getSelicPerMonth()).thenReturn(0.01D);
    Assertions.assertEquals(List.of(taxCalculation),
        taxCalculationService.postByDatePeriod(dateRequestBody.getDate()));
  }

  @Test
  @DisplayName("Delete and post by date period return a taxCalculated change in period")
  void deleteAndPostByDatePeriod_returnTaxCalculatedChangeInPeriod() {
    when(nfeService.findByTimeGap(any(LocalDate.class), any(LocalDate.class))).thenReturn(
        List.of());
    when(taxesService.listAll()).thenReturn(List.of(taxes));
    when(taxCalculationRepository.findByCalculationDate(any(LocalDate.class))).thenReturn(
        List.of(taxCalculation));
    when(taxCalculationRepository.save(any(TaxCalculation.class))).thenReturn(taxCalculation);
    when(selicService.getSelicPerMonth()).thenReturn(0.01D);
    Assertions.assertEquals(List.of(taxCalculation),
        taxCalculationService.postByDatePeriod(dateRequestBody.getDate()));
  }

  @Test
  @DisplayName("Delete by ID deletes a taxCalculated when successful")
  void deleteById_DeletesTaxCalculated_WhenSuccessful() {
    when(taxCalculationRepository.findById(taxCalculation.getId())).thenReturn(
        Optional.of(taxCalculation));
    doNothing().when(taxCalculationRepository).delete(taxCalculation);
    Assertions.assertDoesNotThrow(() -> taxCalculationService.deleteById(id));
    verify(taxCalculationRepository).delete(taxCalculation);
  }

  @Test
  @DisplayName("Delete all by month deletes all taxCalculated in a month")
  void deleteAllByMonth_DeletesAllTaxCalculatedInAMonth() {
    when(taxCalculationRepository.findByCalculationDate(any(LocalDate.class))).thenReturn(
        List.of(taxCalculation));
    doNothing().when(taxCalculationRepository).deleteAll(List.of(taxCalculation));
    Assertions.assertDoesNotThrow(() -> taxCalculationService.deleteAllByMonth(LocalDate.now()));
    verify(taxCalculationRepository).deleteAll(List.of(taxCalculation));

  }
}
