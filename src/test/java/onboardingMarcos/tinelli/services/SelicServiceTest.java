package onboardingMarcos.tinelli.services;

import onboardingMarcos.tinelli.service.SelicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SelicServiceTest {

  @InjectMocks
  private SelicService selicService;

  @Test
  void getSelicPerMonth() {
    Assertions.assertFalse(selicService.getSelicPerMonth().isNaN());
  }

}
