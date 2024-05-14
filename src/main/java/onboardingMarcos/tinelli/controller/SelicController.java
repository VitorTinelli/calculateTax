package onboardingMarcos.tinelli.controller;

import onboardingMarcos.tinelli.service.SelicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selic")
public class SelicController {

  private final SelicService selicService;

  public SelicController(final SelicService selicService) {
    this.selicService = selicService;
  }

  @GetMapping
  public ResponseEntity<Double> getSelicPerMonth() {
    return ResponseEntity.ok(selicService.getSelicPerMonth());
  }
}
