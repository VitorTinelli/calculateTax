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
class NfeTest {

  UUID id;

  @InjectMocks
  private Nfe nfe;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
  }

  @Test
  void testGettersAndSetters() {
    Assertions.assertAll(() -> {
      nfe.setDate(LocalDate.now());
      Assertions.assertEquals(LocalDate.now(), nfe.getDate());
    }, () -> {
      nfe.setId(id);
      Assertions.assertEquals(id, nfe.getId());
    }, () -> {
      nfe.setNumber(123L);
      Assertions.assertEquals(123L, nfe.getNumber());
    }, () -> {
      nfe.setValue(950.00D);
      Assertions.assertEquals(950.00D, nfe.getValue());
    });
  }

  @Test
  void testConstructor() {
    Assertions.assertDoesNotThrow(() -> new Nfe(id, 124914L, LocalDate.now(), 950.00D));
  }

}
