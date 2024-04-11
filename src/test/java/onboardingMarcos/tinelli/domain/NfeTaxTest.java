package onboardingMarcos.tinelli.domain;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NfeTaxTest {

  Nfe nfe;
  Taxes tax;
  UUID id;

  @InjectMocks
  private NfeTax nfeTax;
  
  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    nfe = new Nfe(
        id,
        12345678910L,
        LocalDate.now(),
        198.00D
    );
    tax = new Taxes(
        id,
        "ICMS",
        17.0D
    );
    nfeTax = new NfeTax(
        id,
        nfe,
        tax,
        198.00D,
        0.00D,
        "January",
        2022
    );
  }

  @Test
  void testGettersAndSetters() {
    Assertions.assertAll(
        () -> {
          nfeTax.setId(id);
          Assertions.assertEquals(id, nfeTax.getId());
        },
        () -> {
          nfeTax.setNfe(nfe);
          Assertions.assertEquals(nfe, nfeTax.getNfe());
        },
        () -> {
          nfeTax.setTaxes(tax);
          Assertions.assertEquals(tax, nfeTax.getTaxes());
        },
        () -> {
          nfeTax.setMonth("January");
          Assertions.assertEquals("January", nfeTax.getMonth());
        },
        () -> {
          nfeTax.setYear(2022);
          Assertions.assertEquals(2022, nfeTax.getYear());
        },
        () -> {
          nfeTax.setValueWithTax(198.00D);
          Assertions.assertEquals(198.00D, nfeTax.getValueWithTax());
        },
        () -> {
          nfeTax.setDifference(0.00D);
          Assertions.assertEquals(0.00D, nfeTax.getDifference());
        }
    );
  }

  @Test
  void testConstructor() {
    Assertions.assertDoesNotThrow(() -> nfeTax = new NfeTax(
        id,
        nfe,
        tax,
        198.00D,
        0.00D,
        "January",
        2022
    ));
  }
}
