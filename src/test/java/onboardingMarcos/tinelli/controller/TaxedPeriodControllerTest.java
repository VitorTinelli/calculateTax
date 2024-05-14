package onboardingMarcos.tinelli.controller;

import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxedPeriod;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.DateRequestBody;
import onboardingMarcos.tinelli.service.TaxedPeriodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxedPeriodControllerTest {

  UUID id;
  TaxedPeriod taxedPeriod;
  Taxes taxes;
  DateRequestBody dateRequestBody;

  @InjectMocks
  private TaxedPeriodController taxedPeriodController;

  @Mock
  private TaxedPeriodService taxedPeriodService;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    taxes = new Taxes(id, "ISS", 10);
    taxedPeriod = new TaxedPeriod(id, BigDecimal.valueOf(400D), BigDecimal.valueOf(123.50D),
        LocalDate.now(), taxes);
    dateRequestBody = new DateRequestBody(LocalDate.now());
  }

  @Test
  @DisplayName("List all return all taxedPeriods")
  void listAll_returnAllCalculatedMonths_WhenSuccessful() {
    when(taxedPeriodService.listAll()).thenReturn(
        List.of(taxedPeriod));
    Assertions.assertEquals(taxedPeriodController.listAll().getBody(), List.of(taxedPeriod));
    verify(taxedPeriodService).listAll();
    verifyNoMoreInteractions(taxedPeriodService);
  }

  @Test
  @DisplayName("Find by month return the taxedPeriod")
  void findByMonth_returnCalculatedTaxForTheMonth_WhenSuccessful() {
    when(taxedPeriodService.findByMonth(dateRequestBody.getDate())).thenReturn(
        List.of(taxedPeriod));
    Assertions.assertEquals(
        taxedPeriodController.findByMonth(dateRequestBody).getBody(),
        List.of(taxedPeriod));
    verify(taxedPeriodService).findByMonth(dateRequestBody.getDate());
    verifyNoMoreInteractions(taxedPeriodService);
  }

  @Test
  @DisplayName("Post by date period return the taxedPeriod")
  void postByDatePeriod_returnCalculatedTaxForTheMonth_WhenItIsNotAlreadyRegistered() {
    when(taxedPeriodService.postByDatePeriod(LocalDate.now())).thenReturn(
        List.of(taxedPeriod));
    Assertions.assertEquals(
        taxedPeriodController.postByDatePeriod(dateRequestBody).getBody(),
        List.of(taxedPeriod));
    verify(taxedPeriodService).postByDatePeriod(LocalDate.now());
    verifyNoMoreInteractions(taxedPeriodService);
  }

  @Test
  @DisplayName("Post by date period replaces the taxedPeriod when any chenge is detected")
  void postByDatePeriod_replacesCalculatedTaxForTheMonth_WhenAnyChangeIsDetected() {
    when(taxedPeriodService.postByDatePeriod(LocalDate.now())).thenReturn(
        List.of(taxedPeriod));
    Assertions.assertEquals(
        taxedPeriodController.postByDatePeriod(dateRequestBody).getBody(),
        List.of(taxedPeriod));
    taxedPeriod.setTaxedValue(BigDecimal.valueOf(3000D));
    when(taxedPeriodService.postByDatePeriod(LocalDate.now())).thenReturn(
        List.of(taxedPeriod));
    Assertions.assertEquals(
        taxedPeriodController.postByDatePeriod(dateRequestBody).getBody(),
        List.of(taxedPeriod));
  }

  @Test
  @DisplayName("Post by date period throws BadRequestException when already calculated for this period")
  void postByDatePeriod_throwsBadRequestException_WhenAlreadyCalculatedForThisPeriod() {
    when(taxedPeriodService.postByDatePeriod(LocalDate.now())).thenThrow(
        new BadRequestException("Already calculated for this period."));
    Assertions.assertThrows(BadRequestException.class,
        () -> taxedPeriodController.postByDatePeriod(dateRequestBody));
  }

  @Test
  @DisplayName("Delete by id deletes a taxedPeriod when successful")
  void deleteById_deletesTaxCalculation_WhenSuccessful() {
    doNothing().when(taxedPeriodService).deleteById(id);
    Assertions.assertDoesNotThrow(() -> taxedPeriodController.deleteById(id));
    verify(taxedPeriodService).deleteById(id);
  }

  @Test
  @DisplayName("Delete all by month deletes all taxedPeriods for the month when successful")
  void deleteAllByMonth_deletesAllTaxCalculationsForTheMonth_WhenSuccessful() {
    doNothing().when(taxedPeriodService).deleteAllByMonth(dateRequestBody.getDate());
    Assertions.assertDoesNotThrow(() -> taxedPeriodController.deleteAllByMonth(dateRequestBody));
    verify(taxedPeriodService).deleteAllByMonth(dateRequestBody.getDate());
  }


}
