package onboardingMarcos.tinelli.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SelicControllerTest {

  @InjectMocks
  private SelicController selicController;

  @Test
  void getSelicPerMonth_ReturnsTheSelicAliquotPerMonth() {
    Double selicPerMonth = selicController.getSelicPerMonth();
    Assertions.assertFalse(selicPerMonth.isNaN());
  }
}
