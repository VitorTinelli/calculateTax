package onboardingMarcos.tinelli.domain;


import java.math.BigDecimal;
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
class TaxedPeriodTest {

  UUID id;
  Taxes tax;
  @InjectMocks
  private TaxedPeriod taxedPeriod;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    tax = new Taxes();
  }

  @Test
  @DisplayName("Test all getters and setters methods")
  void testGettersAndSetters() {
    Assertions.assertAll(() -> {
      taxedPeriod.setId(id);
      Assertions.assertEquals(id, taxedPeriod.getId());
    }, () -> {
      taxedPeriod.setTax(tax);
      Assertions.assertEquals(tax, taxedPeriod.getTax());
    }, () -> {
      taxedPeriod.setNfeValue(BigDecimal.valueOf(950.00D));
      Assertions.assertEquals(BigDecimal.valueOf(950.00D), taxedPeriod.getNfeValue());
    }, () -> {
      taxedPeriod.setCalculationDate(LocalDate.now());
      Assertions.assertEquals(LocalDate.now(), taxedPeriod.getCalculationDate());
    }, () -> {
      taxedPeriod.setTaxedValue(BigDecimal.valueOf(950.00D));
      Assertions.assertEquals(BigDecimal.valueOf(950.00D), taxedPeriod.getTaxedValue());
    });
  }

  @Test
  @DisplayName("Test the constructor method")
  void testConstructor() {
    Assertions.assertDoesNotThrow(
        () -> new TaxedPeriod(id, BigDecimal.valueOf(1000D), BigDecimal.valueOf(950.05D),
            LocalDate.now(), tax));
    Assertions.assertDoesNotThrow(() -> new TaxedPeriod());
  }
}