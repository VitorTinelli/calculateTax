package onboardingMarcos.tinelli.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SelicDtoTest {


  @InjectMocks
  private SelicDTO selicDTO;

  @Test
  @DisplayName("Test all getters and setters methods")
  void testGettersAndSetters() {
    Assertions.assertAll(
        () -> {
          selicDTO.setNome("Selic");
          Assertions.assertEquals("Selic", selicDTO.getNome());
        },
        () -> {
          selicDTO.setValor(Double.toString(2.00D));
          Assertions.assertEquals(2.00D, Double.parseDouble(selicDTO.getValor()));
        }
    );
  }

  @Test
  @DisplayName("Test the constructor method")
  void testConstructor() {
    Assertions.assertDoesNotThrow(() -> new SelicDTO(
        "Selic",
        "20.0D"
    ));
  }
  
}
