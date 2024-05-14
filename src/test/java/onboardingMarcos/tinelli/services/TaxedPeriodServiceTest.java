package onboardingMarcos.tinelli.services;

import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxedPeriod;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.repository.TaxedPeriodRepository;
import onboardingMarcos.tinelli.requests.DateRequestBody;
import onboardingMarcos.tinelli.service.NfeService;
import onboardingMarcos.tinelli.service.SelicService;
import onboardingMarcos.tinelli.service.TaxedPeriodService;
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
class TaxedPeriodServiceTest {

  UUID id;
  TaxedPeriod taxedPeriod;
  Taxes taxes;
  DateRequestBody dateRequestBody;

  @InjectMocks
  private TaxedPeriodService taxedPeriodService;

  @Mock
  private TaxedPeriodRepository taxedPeriodRepository;

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
    taxedPeriod = new TaxedPeriod(id, BigDecimal.valueOf(300D), BigDecimal.valueOf(200D),
        LocalDate.now(), taxes);
    dateRequestBody = new DateRequestBody(LocalDate.now());
  }

  @Test
  @DisplayName("List all return all taxedPeriods")
  void listAll_returnAllCalculatedMonths_WhenSuccessful() {
    when(taxedPeriodService.listAll()).thenReturn(
        List.of(taxedPeriod));
    Assertions.assertFalse(taxedPeriodService.listAll().isEmpty());
    verify(taxedPeriodRepository).findAll();
    verifyNoMoreInteractions(taxedPeriodRepository);
  }

  @Test
  @DisplayName("Find by taxedPeriod ID return one taxedPeriod")
  void findBytaxedPeriodId_returntaxedPeriod_WhenSuccessful() {
    when(taxedPeriodRepository.findById(id)).thenReturn(Optional.of(taxedPeriod));
    Assertions.assertEquals(taxedPeriod,
        taxedPeriodService.findByIdOrThrowBadExceptionError(id));
    verify(taxedPeriodRepository).findById(id);
    verifyNoMoreInteractions(taxedPeriodRepository);
  }

  @Test
  @DisplayName("Find by taxedPeriod ID throw BadRequestException when taxedPeriod not found")
  void findByTaxedPeriodId_throwBadRequestException_WhentaxedPeriodNotFound() {
    when(taxedPeriodRepository.findById(id)).thenReturn(Optional.empty());
    Assertions.assertThrows(BadRequestException.class,
        () -> taxedPeriodService.findByIdOrThrowBadExceptionError(id));
  }

  @Test
  @DisplayName("Find by month return a taxedPeriod list when successful")
  void findByMonth_returnTaxedPeriodList_WhenSuccessful() {
    when(taxedPeriodRepository.findByCalculationDate(any(LocalDate.class)))
        .thenReturn(List.of(taxedPeriod));
    Assertions.assertFalse(taxedPeriodService.findByMonth(dateRequestBody.getDate()).isEmpty());
    verifyNoMoreInteractions(taxedPeriodRepository);
  }

  @Test
  @DisplayName("Find by month throw BadRequestException when no taxedPeriod found")
  void findByMonth_throwBadRequestException_WhenNoTaxedPeriodFound() {
    when(taxedPeriodRepository.findByCalculationDate(any(LocalDate.class))).thenReturn(
        List.of());
    Assertions.assertThrows(BadRequestException.class,
        () -> taxedPeriodService.findByMonth(dateRequestBody.getDate()));
  }

  @Test
  @DisplayName("Post by date period return a taxedPeriod if any nfe found")
  void postByDatePeriod_returnTaxedPeriod_WhenAnyNfeFound() {
    when(nfeService.findByTimePeriod(any(LocalDate.class), any(LocalDate.class))).thenReturn(
        List.of());
    when(taxesService.listAll()).thenReturn(List.of(taxes));
    when(taxedPeriodRepository.save(any(TaxedPeriod.class))).thenReturn(taxedPeriod);
    when(selicService.getSelicPerMonth()).thenReturn(0.01D);
    Assertions.assertEquals(List.of(taxedPeriod),
        taxedPeriodService.postByDatePeriod(dateRequestBody.getDate()));
  }

  @Test
  @DisplayName("Delete and post by date period return a taxedPeriod change in period")
  void deleteAndPostByDatePeriod_returnTaxedPeriodChangeInPeriod() {
    when(nfeService.findByTimePeriod(any(LocalDate.class), any(LocalDate.class))).thenReturn(
        List.of());
    when(taxesService.listAll()).thenReturn(List.of(taxes));
    when(taxedPeriodRepository.findByCalculationDate(any(LocalDate.class))).thenReturn(
        List.of(taxedPeriod));
    when(taxedPeriodRepository.save(any(TaxedPeriod.class))).thenReturn(taxedPeriod);
    when(selicService.getSelicPerMonth()).thenReturn(0.01D);
    Assertions.assertEquals(List.of(taxedPeriod),
        taxedPeriodService.postByDatePeriod(dateRequestBody.getDate()));
  }

  @Test
  @DisplayName("Delete by ID deletes a taxedPeriod when successful")
  void deleteById_DeletesTaxedPeriod_WhenSuccessful() {
    when(taxedPeriodRepository.findById(taxedPeriod.getId())).thenReturn(
        Optional.of(taxedPeriod));
    doNothing().when(taxedPeriodRepository).delete(taxedPeriod);
    Assertions.assertDoesNotThrow(() -> taxedPeriodService.deleteById(id));
    verify(taxedPeriodRepository).delete(taxedPeriod);
  }

  @Test
  @DisplayName("Delete all by month deletes all taxedPeriod ")
  void deleteAllByMonth_DeletesAllTaxedPeriodInAMonth() {
    when(taxedPeriodRepository.findByCalculationDate(any(LocalDate.class))).thenReturn(
        List.of(taxedPeriod));
    doNothing().when(taxedPeriodRepository).deleteAll(List.of(taxedPeriod));
    Assertions.assertDoesNotThrow(() -> taxedPeriodService.deleteAllByMonth(LocalDate.now()));
    verify(taxedPeriodRepository).deleteAll(List.of(taxedPeriod));

  }
}
