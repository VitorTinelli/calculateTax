package onboardingMarcos.tinelli.controller;


import static org.mockito.Mockito.when;
import onboardingMarcos.tinelli.service.SelicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class SelicControllerTest {

  @InjectMocks
  private SelicController selicController;

  @Mock
  private SelicService selicService;
  @Test
  void getSelicPerMonth_ReturnsTheSelicAliquotPerMonth() {
    when(selicService.getSelicPerMonth()).thenReturn(0.2D);
    Assertions.assertEquals(ResponseEntity.ok(0.2D), selicController.getSelicPerMonth());
  }
}
