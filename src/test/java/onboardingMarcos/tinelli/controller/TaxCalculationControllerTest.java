package onboardingMarcos.tinelli.controller;

import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import onboardingMarcos.tinelli.domain.TaxCalculation;
import onboardingMarcos.tinelli.domain.Taxes;
import onboardingMarcos.tinelli.exceptions.BadRequestException;
import onboardingMarcos.tinelli.requests.DateRequestBody;
import onboardingMarcos.tinelli.service.TaxCalculationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxCalculationControllerTest {

  UUID id;
  TaxCalculation taxCalculation;
  Taxes taxes;
  DateRequestBody dateRequestBody;

  @InjectMocks
  private TaxCalculationController taxCalculationController;

  @Mock
  private TaxCalculationService taxCalculationService;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    taxes = new Taxes(id, "ISS", 10);
    taxCalculation = new TaxCalculation(id, 1000D, 100D, LocalDate.now(), taxes);
    dateRequestBody = new DateRequestBody(LocalDate.now());
  }

  @Test
  @DisplayName("List all return all calculated months")
  void listAll_returnAllCalculatedMonths_WhenSuccessful() {
    when(taxCalculationService.ListAll()).thenReturn(List.of(taxCalculation));
    Assertions.assertEquals(taxCalculationController.ListAll().getBody(), List.of(taxCalculation));
    verify(taxCalculationService).ListAll();
    verifyNoMoreInteractions(taxCalculationService);
  }

  @Test
  @DisplayName("Post by date period return the calculated tax for the month")
  void postByDatePeriod_returnCalculatedTaxForTheMonth_WhenItIsNotAlreadyRegistered() {
    when(taxCalculationService.postByDatePeriod(LocalDate.now())).thenReturn(
        List.of(taxCalculation));
    Assertions.assertEquals(
        taxCalculationController.postByDatePeriod(dateRequestBody).getBody(),
        List.of(taxCalculation));
    verify(taxCalculationService).postByDatePeriod(LocalDate.now());
    verifyNoMoreInteractions(taxCalculationService);
  }

  @Test
  @DisplayName("Post by date period replaces the calculated tax for the month when any chenge is detected")
  void postByDatePeriod_replacesCalculatedTaxForTheMonth_WhenAnyChangeIsDetected() {
    when(taxCalculationService.postByDatePeriod(LocalDate.now())).thenReturn(
        List.of(taxCalculation));
    Assertions.assertEquals(
        taxCalculationController.postByDatePeriod(dateRequestBody).getBody(),
        List.of(taxCalculation));
    taxCalculation.setTotalTaxedValue(200D);
    when(taxCalculationService.postByDatePeriod(LocalDate.now())).thenReturn(
        List.of(taxCalculation));
    Assertions.assertEquals(
        taxCalculationController.postByDatePeriod(dateRequestBody).getBody(),
        List.of(taxCalculation));
  }

  @Test
  @DisplayName("Post by date period throws BadRequestException when already calculated for this period")
  void postByDatePeriod_throwsBadRequestException_WhenAlreadyCalculatedForThisPeriod() {
    when(taxCalculationService.postByDatePeriod(LocalDate.now())).thenThrow(
        new BadRequestException("Already calculated for this period."));
    Assertions.assertThrows(BadRequestException.class,
        () -> taxCalculationController.postByDatePeriod(dateRequestBody));
  }


}
