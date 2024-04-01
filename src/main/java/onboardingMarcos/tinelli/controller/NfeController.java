package onboardingMarcos.tinelli.controller;

import java.util.List;
import onboardingMarcos.tinelli.domain.Nfe;
import onboardingMarcos.tinelli.service.NfeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nfe")
public class NfeController {

  private final NfeService nfeService;

  public NfeController(NfeService nfeService) {
    this.nfeService = nfeService;
  }

  @GetMapping
  public ResponseEntity<List<Nfe>> listAll() {
    return ResponseEntity.ok(nfeService.listAll());
  }

  
}
