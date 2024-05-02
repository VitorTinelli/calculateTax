package onboardingMarcos.tinelli.domain;


import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxCalculationTest {

  UUID id;
  Taxes tax;
  @InjectMocks
  private TaxCalculation taxCalculation;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    tax = new Taxes();
  }

  @Test
  @DisplayName("Test all getters and setters methods")
  void testGettersAndSetters() {
    Assertions.assertAll(() -> {
      taxCalculation.setId(id);
      Assertions.assertEquals(id, taxCalculation.getId());
    }, () -> {
      taxCalculation.setTax(tax);
      Assertions.assertEquals(tax, taxCalculation.getTax());
    }, () -> {
      taxCalculation.setTotalValue(950.00D);
      Assertions.assertEquals(950.00D, taxCalculation.getTotalValue());
    }, () -> {
      taxCalculation.setCalculationDate(LocalDate.now());
      Assertions.assertEquals(LocalDate.now(), taxCalculation.getCalculationDate());
    }, () -> {
      taxCalculation.setTotalTaxedValue(950.00D);
      Assertions.assertEquals(950.00D, taxCalculation.getTotalTaxedValue());
    });
  }

  @Test
  @DisplayName("Test the constructor method")
  void testConstructor() {
    Assertions.assertDoesNotThrow(
        () -> new TaxCalculation(id, 950.00D, 950.00D, LocalDate.now(), tax));
    Assertions.assertDoesNotThrow(() -> new TaxCalculation());
  }
}