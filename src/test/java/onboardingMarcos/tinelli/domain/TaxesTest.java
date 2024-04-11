package onboardingMarcos.tinelli.domain;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxesTest {

  @InjectMocks
  private Taxes taxes;

  @Test
  void testSetAndGetters() {
    UUID id = UUID.randomUUID();
    Assertions.assertAll("Taxes",
        () -> {
          taxes.setId(id);
          Assertions.assertEquals(id, taxes.getId());
        },
        () -> {
          taxes.setName("ICMS");
          Assertions.assertEquals("ICMS", taxes.getName());
        },
        () -> {
          taxes.setAliquot(17.0D);
          Assertions.assertEquals(17.0D, taxes.getAliquot());
        });
  }

  @Test
  @DisplayName("Test the constructor method")
  void constructorTest() {
    Assertions.assertDoesNotThrow(() -> new Taxes(UUID.randomUUID(), "ICMS", 17.0D));
  }
}
